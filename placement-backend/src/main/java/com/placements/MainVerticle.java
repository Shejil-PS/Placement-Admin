package com.placements;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import com.placements.verticles.HttpServerVerticle;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.deployVerticle(new HttpServerVerticle())
                .onSuccess(id -> startPromise.complete())
                .onFailure(startPromise::fail);
    }
}