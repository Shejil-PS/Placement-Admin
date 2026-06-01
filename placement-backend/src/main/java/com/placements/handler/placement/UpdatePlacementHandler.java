package com.placements.handler.placement;

import com.placements.dto.request.UpdatePlacementRequest;
import com.placements.dto.response.PlacementResponse;
import com.placements.service.PlacementService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UpdatePlacementHandler {

    private final PlacementService service;

    public UpdatePlacementHandler(PlacementService service) {
        this.service = service;
    }

    public void handle(RoutingContext ctx) {
        String id = ctx.pathParam("id");

        JsonObject body;
        try {
            body = ctx.body().asJsonObject();
        } catch (Exception e) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(PlacementResponse.error("Invalid JSON body").toJson().encode());
            return;
        }

        UpdatePlacementRequest req = UpdatePlacementRequest.fromJson(body);

        service.updatePlacement(id, req)
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(PlacementResponse.success("Placement updated", data).toJson().encode()))
                .onFailure(err -> {
                    boolean notFound = err.getMessage() != null && err.getMessage().contains("not found");
                    ctx.response()
                            .setStatusCode(notFound ? 404 : 500)
                            .putHeader("Content-Type", "application/json")
                            .end(PlacementResponse.error(err.getMessage()).toJson().encode());
                });
    }
}

