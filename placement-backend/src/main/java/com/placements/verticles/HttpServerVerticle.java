package com.placements.verticles;

import com.placements.config.RouterConfig;
import com.placements.util.ResponseUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

public class HttpServerVerticle extends AbstractVerticle {

    private static final int PORT = 8080;

    @Override
    public void start(Promise<Void> startPromise) {

        MongoClient mongoClient = MongoClient.createShared(
                vertx,
                MongoConfigHolder.config()
        );

        Router router = RouterConfig.build(vertx, mongoClient);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(PORT)
                .onSuccess(server -> {
                    System.out.println(
                            "Server started on port " + server.actualPort());
                    startPromise.complete();
                })
                .onFailure(startPromise::fail);
    }

    private static final class MongoConfigHolder {

        static io.vertx.core.json.JsonObject config() {
            return new io.vertx.core.json.JsonObject()
                    .put("connection_string", "mongodb://localhost:27017")
                    .put("db_name", "placement_db");
        }
    }
}
