package com.placements.repository;

import com.placements.model.Company;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.UpdateOptions;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyRepository {

    private static final String COLLECTION = "companies";

    private final MongoClient mongoClient;

    public CompanyRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Future<List<Company>> findAll() {
        return mongoClient.find(COLLECTION, new JsonObject())
                .map(list -> list.stream()
                        .map(Company::fromJson)
                        .collect(Collectors.toList()));
    }

    public Future<Company> findById(String id) {
        JsonObject query = new JsonObject().put("_id", id);
        return mongoClient.findOne(COLLECTION, query, null)
                .map(doc -> doc != null ? Company.fromJson(doc) : null);
    }

    public Future<Company> create(Company company) {
        JsonObject doc = company.toJson();
        return mongoClient.insert(COLLECTION, doc)
                .map(ignored -> company);
    }

    public Future<Company> update(String id, JsonObject updateDoc) {
        JsonObject query = new JsonObject().put("_id", id);
        return mongoClient.findOneAndUpdateWithOptions(
                        COLLECTION,
                        query,
                        updateDoc,
                        new io.vertx.ext.mongo.FindOptions(),
                        new UpdateOptions().setReturningNewDocument(true))
                .map(doc -> doc != null ? Company.fromJson(doc) : null);
    }

    public Future<Boolean> delete(String id) {
        JsonObject query = new JsonObject().put("_id", id);
        return mongoClient.removeDocument(COLLECTION, query)
                .map(result -> result.getRemovedCount() > 0);
    }
}

