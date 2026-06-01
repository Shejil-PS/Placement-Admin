package com.placements.handler;

import com.placements.dto.request.CreateBatchRequest;
import com.placements.service.BatchService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class CreateBatchHandler {

    private final BatchService batchService;

    public CreateBatchHandler(BatchService batchService) {
        this.batchService = batchService;
    }

    public void handle(RoutingContext ctx) {
        JsonObject body;
        try {
            body = ctx.body().asJsonObject();
        } catch (Exception e) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(errorBody("Invalid JSON body"));
            return;
        }

        if (body == null) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(errorBody("Request body is required"));
            return;
        }

        CreateBatchRequest request = CreateBatchRequest.fromJson(body);
        String validationError = request.validate();
        if (validationError != null) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(errorBody(validationError));
            return;
        }

        batchService.createBatch(request)
                .onSuccess(batch -> ctx.response()
                        .setStatusCode(201)
                        .putHeader("Content-Type", "application/json")
                        .end(batch.toJson().encode()))
                .onFailure(err -> ctx.response()
                        .setStatusCode(500)
                        .putHeader("Content-Type", "application/json")
                        .end(errorBody(err.getMessage())));
    }

    private String errorBody(String message) {
        return new JsonObject().put("error", message).encode();
    }
}

