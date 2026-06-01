package com.placements.handler.placement;

import com.placements.dto.response.PlacementResponse;
import com.placements.service.PlacementService;
import io.vertx.ext.web.RoutingContext;

public class GetPlacementsHandler {

    private final PlacementService service;

    public GetPlacementsHandler(PlacementService service) {
        this.service = service;
    }

    public void handle(RoutingContext ctx) {
        service.getAllPlacements()
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(PlacementResponse.success(data).toJson().encode()))
                .onFailure(err ->
                        ctx.response()
                                .setStatusCode(500)
                                .putHeader("Content-Type", "application/json")
                                .end(PlacementResponse.error(err.getMessage()).toJson().encode()));
    }
}

