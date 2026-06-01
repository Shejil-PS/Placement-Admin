package com.placements.repository;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

public class ApplicationRepository {

    private static final String COLLECTION = "applications";

    private final MongoClient mongoClient;

    public ApplicationRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    // ── Core CRUD ──────────────────────────────────────────────────────────────

    /**
     * Insert a new application document. Returns the generated _id string.
     */
    public Future<String> insert(JsonObject document) {
        return mongoClient.insert(COLLECTION, document);
    }

    /**
     * Find all applications, optionally filtered.
     * Pass an empty JsonObject to fetch all.
     */
    public Future<List<JsonObject>> findAll(JsonObject query) {
        return mongoClient.find(COLLECTION, query);
    }

    /**
     * Find all applications with pagination and sort support.
     */
    public Future<List<JsonObject>> findAll(JsonObject query, FindOptions options) {
        return mongoClient.findWithOptions(COLLECTION, query, options);
    }

    /**
     * Find a single application by applicationId field (not Mongo _id).
     */
    public Future<JsonObject> findByApplicationId(String applicationId) {
        return mongoClient.findOne(
                COLLECTION,
                new JsonObject().put("applicationId", applicationId),
                null
        );
    }

    /**
     * Find by Mongo ObjectId hex string.
     */
    public Future<JsonObject> findByOid(String oid) {
        return mongoClient.findOne(
                COLLECTION,
                new JsonObject().put("_id", new JsonObject().put("$oid", oid)),
                null
        );
    }

    /**
     * Update only the status field for a given applicationId.
     */
    public Future<JsonObject> updateStatus(String applicationId, String status) {
        JsonObject query  = new JsonObject().put("applicationId", applicationId);
        JsonObject update = new JsonObject().put("$set", new JsonObject().put("status", status));
        return mongoClient.findOneAndUpdate(COLLECTION, query, update);
    }

    /**
     * Hard-delete by applicationId.
     */
    public Future<Void> deleteByApplicationId(String applicationId) {
        return mongoClient
                .removeDocument(COLLECTION, new JsonObject().put("applicationId", applicationId))
                .mapEmpty();
    }

    // ── Query helpers used by ExportApplicationsHandler ───────────────────────

    /**
     * Fetch all applications for a given placement drive.
     */
    public Future<List<JsonObject>> findByPlacementId(String placementId) {
        return mongoClient.find(COLLECTION,
                new JsonObject().put("placementId", placementId));
    }

    /**
     * Fetch all applications for a specific job within a placement.
     */
    public Future<List<JsonObject>> findByPlacementAndJob(String placementId, String jobId) {
        JsonObject query = new JsonObject()
                .put("placementId", placementId)
                .put("jobId", jobId);
        return mongoClient.find(COLLECTION, query);
    }

    /**
     * Fetch all applications for a student.
     */
    public Future<List<JsonObject>> findByStudentId(String studentId) {
        return mongoClient.find(COLLECTION,
                new JsonObject().put("studentId", studentId));
    }

    /**
     * Fetch all applications filtered by status (e.g. "Selected", "Applied").
     */
    public Future<List<JsonObject>> findByStatus(String status) {
        return mongoClient.find(COLLECTION,
                new JsonObject().put("status", status));
    }

    /**
     * Generic filter — callers build the query; repository executes it.
     */
    public Future<List<JsonObject>> findByFilter(JsonObject query) {
        return mongoClient.find(COLLECTION, query);
    }
}
