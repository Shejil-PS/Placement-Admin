package com.placements.handler.application;

import com.placements.dto.request.ApplyForJobRequest;
import com.placements.dto.response.ApplicationResponse;
import com.placements.service.ApplicationService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class ApplyForJobHandler {

    private final ApplicationService service;

    public ApplyForJobHandler(ApplicationService service) {
        this.service = service;
    }

    public void handle(RoutingContext ctx) {
        JsonObject body;
        try {
            body = ctx.body().asJsonObject();
        } catch (Exception e) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(ApplicationResponse.error("Invalid JSON body").encode());
            return;
        }

        ApplyForJobRequest req = ApplyForJobRequest.fromJson(body);
        String validationError = req.validate();
        if (validationError != null) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(ApplicationResponse.error(validationError).encode());
            return;
        }

        service.applyForJob(req)
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(201)
                                .putHeader("Content-Type", "application/json")
                                .end(ApplicationResponse.success("Application submitted", data).encode()))
                .onFailure(err -> {
                    boolean conflict = err.getMessage() != null
                            && err.getMessage().contains("already applied");
                    ctx.response()
                            .setStatusCode(conflict ? 409 : 500)
                            .putHeader("Content-Type", "application/json")
                            .end(ApplicationResponse.error(err.getMessage()).encode());
                });
    }
}

