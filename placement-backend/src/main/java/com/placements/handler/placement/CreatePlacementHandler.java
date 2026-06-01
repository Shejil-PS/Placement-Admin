package com.placements.handler.placement;

import com.placements.dto.request.CreatePlacementRequest;
import com.placements.dto.response.PlacementResponse;
import com.placements.service.PlacementService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class CreatePlacementHandler {

    private final PlacementService service;

    public CreatePlacementHandler(PlacementService service) {
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
                    .end(PlacementResponse.error("Invalid JSON body").toJson().encode());
            return;
        }

        CreatePlacementRequest req = CreatePlacementRequest.fromJson(body);
        String validationError = req.validate();
        if (validationError != null) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(PlacementResponse.error(validationError).toJson().encode());
            return;
        }

        service.createPlacement(req)
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(201)
                                .putHeader("Content-Type", "application/json")
                                .end(PlacementResponse.success("Placement created", data).toJson().encode()))
                .onFailure(err ->
                        ctx.response()
                                .setStatusCode(500)
                                .putHeader("Content-Type", "application/json")
                                .end(PlacementResponse.error(err.getMessage()).toJson().encode()));
    }
}

