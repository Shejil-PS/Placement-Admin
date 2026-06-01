package com.placements.handler.application;

import com.placements.dto.response.ApplicationResponse;
import com.placements.service.ApplicationService;
import io.vertx.ext.web.RoutingContext;

public class GetApplicationByIdHandler {

    private final ApplicationService service;

    public GetApplicationByIdHandler(ApplicationService service) {
        this.service = service;
    }

    /**
     * GET /applications/:applicationId
     */
    public void handle(RoutingContext ctx) {
        String applicationId = ctx.pathParam("applicationId");

        service.getApplicationById(applicationId)
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(ApplicationResponse.success(data).encode()))
                .onFailure(err -> {
                    boolean notFound = err.getMessage() != null
                            && err.getMessage().contains("not found");
                    ctx.response()
                            .setStatusCode(notFound ? 404 : 500)
                            .putHeader("Content-Type", "application/json")
                            .end(ApplicationResponse.error(err.getMessage()).encode());
                });
    }
}

