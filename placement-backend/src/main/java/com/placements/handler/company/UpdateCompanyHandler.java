package com.placements.handler;

import com.placements.dto.request.UpdateCompanyRequest;
import com.placements.service.CompanyService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UpdateCompanyHandler {

    private final CompanyService companyService;

    public UpdateCompanyHandler(CompanyService companyService) {
        this.companyService = companyService;
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
                    .end(errorBody("Invalid JSON body"));
            return;
        }

        if (body == null) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(errorBody("Request body is required"));
            return;
        }

        UpdateCompanyRequest request = UpdateCompanyRequest.fromJson(body);

        companyService.updateCompany(id, request)
                .onSuccess(company -> ctx.response()
                        .setStatusCode(200)
                        .putHeader("Content-Type", "application/json")
                        .end(company.toJson().encode()))
                .onFailure(err -> {
                    String msg = err.getMessage();
                    int status = (msg != null && msg.contains("not found")) ? 404
                            : (msg != null && msg.contains("No fields")) ? 400 : 500;
                    ctx.response()
                            .setStatusCode(status)
                            .putHeader("Content-Type", "application/json")
                            .end(errorBody(msg));
                });
    }

    private String errorBody(String message) {
        return new JsonObject().put("error", message).encode();
    }
}

