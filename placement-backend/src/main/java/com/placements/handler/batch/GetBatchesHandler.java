package com.placements.handler;

import com.placements.service.BatchService;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class GetBatchesHandler {

    private final BatchService batchService;

    public GetBatchesHandler(BatchService batchService) {
        this.batchService = batchService;
    }

    public void handle(RoutingContext ctx) {
        batchService.getAllBatches()
                .onSuccess(batches -> {
                    JsonArray array = new JsonArray();
                    batches.forEach(b -> array.add(b.toJson()));
                    ctx.response()
                            .setStatusCode(200)
                            .putHeader("Content-Type", "application/json")
                            .end(array.encode());
                })
                .onFailure(err -> ctx.response()
                        .setStatusCode(500)
                        .putHeader("Content-Type", "application/json")
                        .end(errorBody(err.getMessage())));
    }

    private String errorBody(String message) {
        return new JsonObject().put("error", message).encode();
    }
}

