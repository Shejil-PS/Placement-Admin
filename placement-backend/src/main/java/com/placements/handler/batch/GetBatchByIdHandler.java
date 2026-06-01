package com.placements.handler;

import com.placements.service.BatchService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class GetBatchByIdHandler {

    private final BatchService batchService;

    public GetBatchByIdHandler(BatchService batchService) {
        this.batchService = batchService;
    }

    public void handle(RoutingContext ctx) {
        String oid = ctx.pathParam("id");

        batchService.getBatchById(oid)
                .onSuccess(batch -> ctx.response()
                        .setStatusCode(200)
                        .putHeader("Content-Type", "application/json")
                        .end(batch.toJson().encode()))
                .onFailure(err -> {
                    String msg = err.getMessage();
                    int status = (msg != null && msg.contains("not found")) ? 404 : 500;
                    ctx.response()
                            .setStatusCode(status)
                            .putHeader("Content-Type", "application/json")
                            .end(errorBody(msg));
                });
    }

    private String errorBody(String message) {
        return new JsonObject().put("error", message).encode();
    }
}

