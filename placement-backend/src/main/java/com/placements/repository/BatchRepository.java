package com.placements.repository;

import com.placements.model.Batch;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.UpdateOptions;

import java.util.List;
import java.util.stream.Collectors;

public class BatchRepository {

    private static final String COLLECTION = "batches";

    private final MongoClient mongoClient;

    public BatchRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Future<List<Batch>> findAll() {
        return mongoClient.find(COLLECTION, new JsonObject())
                .map(list -> list.stream()
                        .map(Batch::fromJson)
                        .collect(Collectors.toList()));
    }

    private JsonObject buildQuery(String id) {
        io.vertx.core.json.JsonArray orArray = new io.vertx.core.json.JsonArray();
        if (id != null && id.length() == 24 && id.matches("^[0-9a-fA-F]{24}$")) {
            orArray.add(new JsonObject().put("_id", new JsonObject().put("$oid", id)));
        }
        orArray.add(new JsonObject().put("_id", id));
        orArray.add(new JsonObject().put("_id ", id));
        return new JsonObject().put("$or", orArray);
    }

    /**
     * Lookup by the ObjectId string (oid), queried as { "_id": { "$oid": "..." } }
     */
    public Future<Batch> findById(String oid) {
        return mongoClient.findOne(COLLECTION, buildQuery(oid), null)
                .map(doc -> doc != null ? Batch.fromJson(doc) : null);
    }

    /**
     * Lookup by the business key field "_id " (trailing space).
     */
    public Future<Batch> findByBatchId(String batchId) {
        JsonObject query = new JsonObject().put("_id ", batchId);
        return mongoClient.findOne(COLLECTION, query, null)
                .map(doc -> doc != null ? Batch.fromJson(doc) : null);
    }

    public Future<Batch> create(Batch batch) {
        // Build the document — let MongoDB generate the ObjectId
        JsonObject doc = new JsonObject();
        doc.put("_id ", batch.getBatchId());   // trailing space preserved
        doc.put("batchCode", batch.getBatchCode());
        doc.put("batchName", batch.getBatchName());
        doc.put("department", batch.getDepartment());

        return mongoClient.insert(COLLECTION, doc)
                .compose(insertedId -> {
                    batch.setOid(insertedId);
                    return Future.succeededFuture(batch);
                });
    }

    /**
     * Update by ObjectId. Returns the updated document.
     */
    public Future<Batch> update(String oid, JsonObject updateDoc) {
        return mongoClient.findOneAndUpdateWithOptions(
                        COLLECTION,
                        buildQuery(oid),
                        updateDoc,
                        new io.vertx.ext.mongo.FindOptions(),
                        new UpdateOptions().setReturningNewDocument(true))
                .map(doc -> doc != null ? Batch.fromJson(doc) : null);
    }

    /**
     * Delete by ObjectId.
     */
    public Future<Boolean> delete(String oid) {
        return mongoClient.removeDocument(COLLECTION, buildQuery(oid))
                .map(result -> result.getRemovedCount() > 0);
    }
}

