package com.placements.handler.application;

import com.placements.dto.response.ApplicationResponse;
import com.placements.service.ApplicationService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UpdateApplicationStatusHandler {

    private final ApplicationService service;

    public UpdateApplicationStatusHandler(ApplicationService service) {
        this.service = service;
    }

    /**
     * PATCH /applications/:applicationId/status
     *
     * Body: { "status": "Selected" }
     */
    public void handle(RoutingContext ctx) {
        String applicationId = ctx.pathParam("applicationId");

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

        String status = body.getString("status");
        if (status == null || status.isBlank()) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(ApplicationResponse.error("status field is required").encode());
            return;
        }

        service.updateStatus(applicationId, status)
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(ApplicationResponse.success("Status updated", data).encode()))
                .onFailure(err -> {
                    String msg = err.getMessage();
                    boolean notFound  = msg != null && msg.contains("not found");
                    boolean badStatus = msg != null && msg.contains("Invalid status");
                    int code = notFound ? 404 : badStatus ? 422 : 500;
                    ctx.response()
                            .setStatusCode(code)
                            .putHeader("Content-Type", "application/json")
                            .end(ApplicationResponse.error(msg).encode());
                });
    }
}

