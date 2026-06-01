package com.placements.router;

import com.placements.handler.*;
import com.placements.repository.CompanyRepository;
import com.placements.service.CompanyService;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class CompanyRouter {

    private final Router router;

    public CompanyRouter(Router router, MongoClient mongoClient) {
        this.router = router;

        CompanyRepository repository = new CompanyRepository(mongoClient);
        CompanyService service = new CompanyService(repository);

        GetCompaniesHandler    getCompaniesHandler    = new GetCompaniesHandler(service);
        GetCompanyByIdHandler  getCompanyByIdHandler  = new GetCompanyByIdHandler(service);
        CreateCompanyHandler   createCompanyHandler   = new CreateCompanyHandler(service);
        UpdateCompanyHandler   updateCompanyHandler   = new UpdateCompanyHandler(service);
        DeleteCompanyHandler   deleteCompanyHandler   = new DeleteCompanyHandler(service);

        router.route("/api/companies*").handler(BodyHandler.create());

        router.get("/api/companies")        .handler(getCompaniesHandler::handle);
        router.get("/api/companies/:id")    .handler(getCompanyByIdHandler::handle);
        router.post("/api/companies")       .handler(createCompanyHandler::handle);
        router.put("/api/companies/:id")    .handler(updateCompanyHandler::handle);
        router.delete("/api/companies/:id") .handler(deleteCompanyHandler::handle);
    }

    public Router getRouter() {
        return router;
    }
}

