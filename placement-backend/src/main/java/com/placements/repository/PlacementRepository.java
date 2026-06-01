package com.placements.repository;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.UpdateOptions;

import java.util.List;

public class PlacementRepository {

    private static final String COLLECTION = "placements";

    private final MongoClient mongoClient;

    public PlacementRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    // ── Placement CRUD ─────────────────────────────────────────────────────────

    public Future<List<JsonObject>> findAll() {
        return mongoClient.find(COLLECTION, new JsonObject());
    }

    public Future<JsonObject> findById(String id) {
        return mongoClient.findOne(COLLECTION, new JsonObject().put("_id", id), null);
    }

    public Future<String> insert(JsonObject document) {
        return mongoClient.insert(COLLECTION, document);
    }

    public Future<JsonObject> updateById(String id, JsonObject updateDoc) {
        JsonObject query = new JsonObject().put("_id", id);
        return mongoClient.findOneAndUpdate(COLLECTION, query, updateDoc);
    }

    public Future<MongoClient> deleteById(String id) {
        return mongoClient.removeDocument(COLLECTION, new JsonObject().put("_id", id))
                .map(result -> mongoClient);
    }

    // ── Job operations ─────────────────────────────────────────────────────────

    /**
     * Push a new job into the jobs array.
     */
    public Future<JsonObject> addJob(String placementId, JsonObject job) {
        JsonObject query = new JsonObject().put("_id", placementId);
        JsonObject update = new JsonObject().put("$push", new JsonObject().put("jobs", job));
        return mongoClient.findOneAndUpdate(COLLECTION, query, update);
    }

    /**
     * Update scalar fields of an existing job identified by jobId.
     */
    public Future<JsonObject> updateJob(String placementId, String jobId, JsonObject fields) {
        JsonObject query = new JsonObject()
                .put("_id", placementId)
                .put("jobs.jobId", jobId);

        JsonObject setFields = new JsonObject();
        fields.forEach(entry -> {
            if (entry.getValue() != null && !entry.getKey().equals("jobId")) {
                setFields.put("jobs.$." + entry.getKey(), entry.getValue());
            }
        });

        JsonObject update = new JsonObject().put("$set", setFields);
        return mongoClient.findOneAndUpdate(COLLECTION, query, update);
    }

    /**
     * Pull (remove) a job from the jobs array by jobId.
     */
    public Future<JsonObject> deleteJob(String placementId, String jobId) {
        JsonObject query = new JsonObject().put("_id", placementId);
        JsonObject update = new JsonObject().put("$pull",
                new JsonObject().put("jobs", new JsonObject().put("jobId", jobId)));
        return mongoClient.findOneAndUpdate(COLLECTION, query, update);
    }

    // ── Job Field operations ───────────────────────────────────────────────────

    /**
     * Push a new field into jobs[matched].fields using the positional $ operator.
     * Requires jobId match in the query.
     */
    public Future<JsonObject> addJobField(String placementId, String jobId, JsonObject field) {
        JsonObject query = new JsonObject()
                .put("_id", placementId)
                .put("jobs.jobId", jobId);
        JsonObject update = new JsonObject().put("$push",
                new JsonObject().put("jobs.$.fields", field));
        return mongoClient.findOneAndUpdate(COLLECTION, query, update);
    }

    /**
     * Update scalar fields of an existing field identified by fieldId.
     * Uses arrayFilters via the raw command because Vert.x MongoClient does not
     * expose arrayFilters directly; falls back to a two-level positional approach
     * where we update each key individually.
     *
     * NOTE: For full arrayFilter support wrap MongoClient with a raw command driver call.
     */
    public Future<JsonObject> updateJobField(String placementId, String jobId,
                                              String fieldId, JsonObject fieldUpdates) {
        // We rebuild the whole fields array on the matched job using $set on jobs.$.fields
        // is not possible without arrayFilters. We instead pull + push as an atomic-ish pair
        // via two sequential updates. Production code should use the MongoDB Java driver directly
        // for arrayFilters support.
        JsonObject pullQuery = new JsonObject()
                .put("_id", placementId)
                .put("jobs.jobId", jobId);

        JsonObject pull = new JsonObject().put("$pull",
                new JsonObject().put("jobs.$.fields",
                        new JsonObject().put("fieldId", fieldId)));

        return mongoClient.findOneAndUpdate(COLLECTION, pullQuery, pull)
                .compose(ignored -> {
                    JsonObject pushUpdate = new JsonObject().put("$push",
                            new JsonObject().put("jobs.$.fields", fieldUpdates));
                    return mongoClient.findOneAndUpdate(COLLECTION, pullQuery, pushUpdate);
                });
    }

    /**
     * Pull (remove) a field from jobs[matched].fields by fieldId.
     */
    public Future<JsonObject> deleteJobField(String placementId, String jobId, String fieldId) {
        JsonObject query = new JsonObject()
                .put("_id", placementId)
                .put("jobs.jobId", jobId);
        JsonObject update = new JsonObject().put("$pull",
                new JsonObject().put("jobs.$.fields",
                        new JsonObject().put("fieldId", fieldId)));
        return mongoClient.findOneAndUpdate(COLLECTION, query, update);
    }
}
