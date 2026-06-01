package com.placements.handler;

import com.placements.service.CompanyService;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class GetCompaniesHandler {

    private final CompanyService companyService;

    public GetCompaniesHandler(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void handle(RoutingContext ctx) {
        companyService.getAllCompanies()
                .onSuccess(companies -> {
                    JsonArray array = new JsonArray();
                    companies.forEach(c -> array.add(c.toJson()));
                    ctx.response()
                            .setStatusCode(200)
                            .putHeader("Content-Type", "application/json")
                            .end(array.encode());
                })
                .onFailure(err -> ctx.response()
                        .setStatusCode(500)
                        .putHeader("Content-Type", "application/json")
                        .end(errorBody(err.getMessage())));
    }

    private String errorBody(String message) {
        return new JsonObject().put("error", message).encode();
    }
}

