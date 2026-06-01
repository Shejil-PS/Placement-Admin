package com.placements.handler;

import com.placements.service.BatchService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class DeleteBatchHandler {

    private final BatchService batchService;

    public DeleteBatchHandler(BatchService batchService) {
        this.batchService = batchService;
    }

    public void handle(RoutingContext ctx) {
        String oid = ctx.pathParam("id");

        batchService.deleteBatch(oid)
                .onSuccess(v -> ctx.response()
                        .setStatusCode(204)
                        .end())
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

