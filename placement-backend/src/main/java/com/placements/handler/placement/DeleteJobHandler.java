package com.placements.handler.placement;

import com.placements.dto.response.PlacementResponse;
import com.placements.service.PlacementService;
import io.vertx.ext.web.RoutingContext;

public class DeleteJobHandler {

    private final PlacementService service;

    public DeleteJobHandler(PlacementService service) {
        this.service = service;
    }

    public void handle(RoutingContext ctx) {
        String placementId = ctx.pathParam("id");
        String jobId = ctx.pathParam("jobId");

        service.deleteJob(placementId, jobId)
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(PlacementResponse.success("Job deleted", data).toJson().encode()))
                .onFailure(err -> {
                    boolean notFound = err.getMessage() != null && err.getMessage().contains("not found");
                    ctx.response()
                            .setStatusCode(notFound ? 404 : 500)
                            .putHeader("Content-Type", "application/json")
                            .end(PlacementResponse.error(err.getMessage()).toJson().encode());
                });
    }
}

