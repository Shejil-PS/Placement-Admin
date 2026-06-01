package com.placements.handler.placement;

import com.placements.dto.response.PlacementResponse;
import com.placements.service.PlacementService;
import io.vertx.ext.web.RoutingContext;

public class DeleteJobFieldHandler {

    private final PlacementService service;

    public DeleteJobFieldHandler(PlacementService service) {
        this.service = service;
    }

    public void handle(RoutingContext ctx) {
        String placementId = ctx.pathParam("id");
        String jobId = ctx.pathParam("jobId");
        String fieldId = ctx.pathParam("fieldId");

        service.deleteJobField(placementId, jobId, fieldId)
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(PlacementResponse.success("Field deleted", data).toJson().encode()))
                .onFailure(err -> {
                    boolean notFound = err.getMessage() != null && err.getMessage().contains("not found");
                    ctx.response()
                            .setStatusCode(notFound ? 404 : 500)
                            .putHeader("Content-Type", "application/json")
                            .end(PlacementResponse.error(err.getMessage()).toJson().encode());
                });
    }
}

