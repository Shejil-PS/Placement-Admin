package com.placements.handler.application;

import com.placements.dto.response.ApplicationResponse;
import com.placements.service.ApplicationService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class GetApplicationsHandler {

    private final ApplicationService service;

    public GetApplicationsHandler(ApplicationService service) {
        this.service = service;
    }

    /**
     * GET /applications
     *
     * Optional query params for filtering:
     *   ?placementId=P001&jobId=J001&companyId=C001&studentId=S001&status=Selected
     */
    public void handle(RoutingContext ctx) {
        JsonObject filters = new JsonObject();

        extractParam(ctx, filters, "placementId");
        extractParam(ctx, filters, "jobId");
        extractParam(ctx, filters, "companyId");
        extractParam(ctx, filters, "studentId");
        extractParam(ctx, filters, "status");

        service.getAllApplications(filters)
                .onSuccess(data ->
                        ctx.response()
                                .setStatusCode(200)
                                .putHeader("Content-Type", "application/json")
                                .end(ApplicationResponse.success(data).encode()))
                .onFailure(err ->
                        ctx.response()
                                .setStatusCode(500)
                                .putHeader("Content-Type", "application/json")
                                .end(ApplicationResponse.error(err.getMessage()).encode()));
    }

    private void extractParam(RoutingContext ctx, JsonObject filters, String key) {
        String val = ctx.queryParam(key).stream().findFirst().orElse(null);
        if (val != null && !val.isBlank()) filters.put(key, val);
    }
}

