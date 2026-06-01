package com.placements;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class MainVerticleTest {

    @Test
    void verticle_deployed(Vertx vertx, VertxTestContext testContext) {
        vertx.deployVerticle(new MainVerticle())
                .onComplete(testContext.succeeding(id -> testContext.completeNow()));
    }
}
