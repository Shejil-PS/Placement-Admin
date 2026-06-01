package com.placements.handler.placement;

import com.placements.dto.request.AddPlacementJobFieldRequest;
import com.placements.dto.response.PlacementResponse;
import com.placements.service.PlacementService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UpdateJobFieldHandler {

    private final PlacementService service;

    public UpdateJobFieldHandler(PlacementService service) {
        this.service = service;
    }

    public void handle(RoutingContext ctx) {
        String placementId = ctx.pathParam("id");
        String jobId = ctx.pathParam("jobId");
        String fieldId = ctx.pathParam("fieldId");

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

        service.updateJobField(placementId, jobId, fieldId, req)
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(PlacementResponse.success("Field updated", data).toJson().encode()))
                .onFailure(err -> {
                    boolean notFound = err.getMessage() != null && err.getMessage().contains("not found");
                    ctx.response()
                            .setStatusCode(notFound ? 404 : 500)
                            .putHeader("Content-Type", "application/json")
                            .end(PlacementResponse.error(err.getMessage()).toJson().encode());
                });
    }
}

