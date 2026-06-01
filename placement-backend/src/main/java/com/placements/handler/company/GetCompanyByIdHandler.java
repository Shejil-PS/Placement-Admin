package com.placements.handler;

import com.placements.service.CompanyService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class GetCompanyByIdHandler {

    private final CompanyService companyService;

    public GetCompanyByIdHandler(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void handle(RoutingContext ctx) {
        String id = ctx.pathParam("id");

        companyService.getCompanyById(id)
                .onSuccess(company -> ctx.response()
                        .setStatusCode(200)
                        .putHeader("Content-Type", "application/json")
                        .end(company.toJson().encode()))
                .onFailure(err -> {
                    String msg = err.getMessage();
                    int status = (msg != null && msg.contains("not found")) ? 404 : 500;
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

