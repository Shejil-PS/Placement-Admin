package com.placements.config;

import io.vertx.core.json.JsonObject;

public final class AppConfig {

    private AppConfig() {
    }

    public static int getPort() {
        return 8080;
    }

    public static JsonObject getMongoConfig() {
        return new JsonObject()
                .put("connection_string", "mongodb://localhost:27017")
                .put("db_name", "placement_db");
    }
}