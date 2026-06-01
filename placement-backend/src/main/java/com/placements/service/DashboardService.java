package com.placements.service;

import com.placements.model.DashboardStats;
import com.placements.repository.ApplicationRepository;
import com.placements.repository.BatchRepository;
import com.placements.repository.CompanyRepository;
import com.placements.repository.PlacementRepository;
import com.placements.repository.StudentRepository;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

/**
 * Business-logic layer for the Dashboard module.
 *
 * <p>All methods return a non-blocking {@link Future} and are safe to call
 * from a Vert.x event-loop thread.  Heavy aggregation queries use the
 * {@link MongoClient} directly so we can send raw pipeline commands that the
 * higher-level repository helpers do not expose.
 */
public class DashboardService {

    private final StudentRepository     studentRepository;
    private final BatchRepository       batchRepository;
    private final CompanyRepository     companyRepository;
    private final PlacementRepository   placementRepository;
    private final ApplicationRepository applicationRepository;
    private final MongoClient           mongoClient;

    /**
     * Constructor injection — all dependencies must be non-null.
     *
     * @param studentRepository     repository for {@code students} collection
     * @param batchRepository       repository for {@code batches} collection
     * @param companyRepository     repository for {@code companies} collection
     * @param placementRepository   repository for {@code placements} collection
     * @param applicationRepository repository for {@code applications} collection
     * @param mongoClient           raw client used for aggregation pipelines
     */
    public DashboardService(StudentRepository     studentRepository,
                            BatchRepository       batchRepository,
                            CompanyRepository     companyRepository,
                            PlacementRepository   placementRepository,
                            ApplicationRepository applicationRepository,
                            MongoClient           mongoClient) {
        this.studentRepository     = studentRepository;
        this.batchRepository       = batchRepository;
        this.companyRepository     = companyRepository;
        this.placementRepository   = placementRepository;
        this.applicationRepository = applicationRepository;
        this.mongoClient           = mongoClient;
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /**
     * Returns cross-collection document counts in a single {@link DashboardStats}.
     *
     * <p>Five {@code find()} calls are fired concurrently via
     * {@link CompositeFuture#all(Future, Future, Future, Future, Future)} so
     * the total latency equals the slowest individual query, not their sum.
     *
     * <p><b>Repository methods used:</b>
     * <ul>
     *   <li>{@code StudentRepository#findAll()}</li>
     *   <li>{@code BatchRepository#findAll()}</li>
     *   <li>{@code CompanyRepository#findAll()}</li>
     *   <li>{@code PlacementRepository#findAll()}</li>
     *   <li>{@code ApplicationRepository#findAll(JsonObject)}</li>
     * </ul>
     *
     * @return a {@code Future} that resolves to {@link DashboardStats}
     */
    public Future<DashboardStats> getSummary() {
        Future<Long> studentCount     = studentRepository.findAll()
                                            .map(list -> (long) list.size());
        Future<Long> batchCount       = batchRepository.findAll()
                                            .map(list -> (long) list.size());
        Future<Long> companyCount     = companyRepository.findAll()
                                            .map(list -> (long) list.size());
        Future<Long> placementCount   = placementRepository.findAll()
                                            .map(list -> (long) list.size());
        Future<Long> applicationCount = applicationRepository
                                            .findAll(new JsonObject())
                                            .map(list -> (long) list.size());

        return CompositeFuture.all(
                        studentCount,
                        batchCount,
                        companyCount,
                        placementCount,
                        applicationCount)
                .map(cf -> new DashboardStats(
                        cf.resultAt(0),
                        cf.resultAt(1),
                        cf.resultAt(2),
                        cf.resultAt(3),
                        cf.resultAt(4)));
    }

    /**
     * Returns per-placement application counts and status breakdowns using a
     * MongoDB aggregation pipeline on the {@code applications} collection.
     *
     * <p>Pipeline stages:
     * <ol>
     *   <li>{@code $group} — group by {@code placementId}, count total
     *       applications and build a status-frequency sub-document.</li>
     *   <li>{@code $sort}  — order by total descending.</li>
     * </ol>
     *
     * <p>Sample output document:
     * <pre>{@code
     * {
     *   "_id": "placement_001",
     *   "totalApplications": 42,
     *   "statusBreakdown": [
     *     { "status": "Selected", "count": 10 },
     *     { "status": "Applied",  "count": 32 }
     *   ]
     * }
     * }</pre>
     *
     * @return a {@code Future} containing a list of placement-stat documents
     */
    public Future<List<JsonObject>> getPlacementStats() {
        JsonObject groupStage = new JsonObject().put("$group", new JsonObject()
                .put("_id", "$placementId")
                .put("totalApplications", new JsonObject().put("$sum", 1))
                .put("statusBreakdown", new JsonObject()
                        .put("$push", new JsonObject().put("status", "$status"))));

        JsonObject sortStage = new JsonObject()
                .put("$sort", new JsonObject().put("totalApplications", -1));

        JsonArray pipeline = new JsonArray().add(groupStage).add(sortStage);

        JsonObject command = new JsonObject()
                .put("aggregate", "applications")
                .put("pipeline", pipeline)
                .put("cursor", new JsonObject());

        return mongoClient.runCommand("aggregate", command)
                .map(result -> {
                    JsonObject cursor = result.getJsonObject("cursor", new JsonObject());
                    return cursor.getJsonArray("firstBatch", new JsonArray())
                                 .stream()
                                 .map(o -> (JsonObject) o)
                                 .toList();
                });
    }

    /**
     * Returns the 10 most recently created applications, sorted by
     * {@code createdAt} descending.
     *
     * <p><b>Repository method used:</b>
     * {@code ApplicationRepository#findAll(JsonObject, FindOptions)}
     *
     * <p><b>Note:</b> This assumes application documents contain a
     * {@code createdAt} field (epoch-millis long or ISO-8601 string).
     * If your schema uses a different timestamp field, adjust the sort key
     * in {@code FindOptions} accordingly.
     *
     * @return a {@code Future} containing up to 10 recent application documents
     */
    public Future<List<JsonObject>> getRecentApplications() {
        FindOptions options = new FindOptions()
                .setSort(new JsonObject().put("createdAt", -1))
                .setLimit(10);

        return applicationRepository.findAll(new JsonObject(), options);
    }
}

