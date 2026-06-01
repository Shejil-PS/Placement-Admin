package com.placements.handler;

import com.placements.dto.request.CreateCompanyRequest;
import com.placements.service.CompanyService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class CreateCompanyHandler {

    private final CompanyService companyService;

    public CreateCompanyHandler(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void handle(RoutingContext ctx) {
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

        CreateCompanyRequest request = CreateCompanyRequest.fromJson(body);
        String validationError = request.validate();
        if (validationError != null) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(errorBody(validationError));
            return;
        }

        companyService.createCompany(request)
                .onSuccess(company -> ctx.response()
                        .setStatusCode(201)
                        .putHeader("Content-Type", "application/json")
                        .end(company.toJson().encode()))
                .onFailure(err -> ctx.response()
                        .setStatusCode(500)
                        .putHeader("Content-Type", "application/json")
                        .end(errorBody(err.getMessage())));
    }

    private String errorBody(String message) {
        return new JsonObject().put("error", message).encode();
    }
}

