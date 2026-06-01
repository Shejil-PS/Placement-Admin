package com.placements.repository;

import com.placements.model.Student;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;
import java.util.stream.Collectors;

public class StudentRepository {

    private static final String COLLECTION = "students";

    private final MongoClient mongoClient;

    public StudentRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public Future<List<Student>> findAll() {
        return mongoClient.find(COLLECTION, new JsonObject())
                .map(list -> list.stream()
                        .map(Student::fromJson)
                        .collect(Collectors.toList()));
    }

    public Future<Student> findById(String id) {
        JsonObject query = new JsonObject().put("_id", id);
        return mongoClient.findOne(COLLECTION, query, null)
                .map(doc -> doc != null ? Student.fromJson(doc) : null);
    }

    public Future<Student> create(Student student) {
        JsonObject doc = student.toJson();
        return mongoClient.insert(COLLECTION, doc)
                .map(insertedId -> {
                    if (student.getId() == null) {
                        student.setId(insertedId);
                    }
                    return student;
                });
    }

    public Future<Student> update(String id, JsonObject updateDoc) {
        JsonObject query = new JsonObject().put("_id", id);
        JsonObject opts = new JsonObject()
                .put("returnNewDocument", true);
        return mongoClient.findOneAndUpdateWithOptions(
                        COLLECTION,
                        query,
                        updateDoc,
                        new io.vertx.ext.mongo.FindOptions(),
                        new io.vertx.ext.mongo.UpdateOptions().setReturningNewDocument(true))
                .map(doc -> doc != null ? Student.fromJson(doc) : null);
    }

    public Future<Boolean> delete(String id) {
        JsonObject query = new JsonObject().put("_id", id);
        return mongoClient.removeDocument(COLLECTION, query)
                .map(result -> result.getRemovedCount() > 0);
    }
}

