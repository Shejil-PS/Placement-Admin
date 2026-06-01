package com.placements.handler.placement;

import com.placements.dto.response.PlacementResponse;
import com.placements.service.PlacementService;
import io.vertx.ext.web.RoutingContext;

public class DeletePlacementHandler {

    private final PlacementService service;

    public DeletePlacementHandler(PlacementService service) {
        this.service = service;
    }

    public void handle(RoutingContext ctx) {
        String id = ctx.pathParam("id");

        service.deletePlacement(id)
                .onSuccess(ignored ->
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(PlacementResponse.success("Placement deleted", null).toJson().encode()))
                .onFailure(err -> {
                    boolean notFound = err.getMessage() != null && err.getMessage().contains("not found");
                    ctx.response()
                            .setStatusCode(notFound ? 404 : 500)
                            .putHeader("Content-Type", "application/json")
                            .end(PlacementResponse.error(err.getMessage()).toJson().encode());
                });
    }
}

