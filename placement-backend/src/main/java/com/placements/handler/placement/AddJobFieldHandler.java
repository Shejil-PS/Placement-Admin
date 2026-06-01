package com.placements.handler.placement;

import com.placements.dto.request.AddPlacementJobFieldRequest;
import com.placements.dto.response.PlacementResponse;
import com.placements.service.PlacementService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AddJobFieldHandler {

    private final PlacementService service;

    public AddJobFieldHandler(PlacementService service) {
        this.service = service;
    }

    public void handle(RoutingContext ctx) {
        String placementId = ctx.pathParam("id");
        String jobId = ctx.pathParam("jobId");

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

        AddPlacementJobFieldRequest req = AddPlacementJobFieldRequest.fromJson(body);
        String validationError = req.validate();
        if (validationError != null) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(PlacementResponse.error(validationError).toJson().encode());
            return;
        }

        service.addJobField(placementId, jobId, req)
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(201)
                                .putHeader("Content-Type", "application/json")
                                .end(PlacementResponse.success("Field added", data).toJson().encode()))
                .onFailure(err -> {
                    boolean notFound = err.getMessage() != null && err.getMessage().contains("not found");
                    ctx.response()
                            .setStatusCode(notFound ? 404 : 500)
                            .putHeader("Content-Type", "application/json")
                            .end(PlacementResponse.error(err.getMessage()).toJson().encode());
                });
    }
}

