package com.placements.router;

import com.placements.handler.application.*;
import com.placements.repository.ApplicationRepository;
import com.placements.service.ApplicationService;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class ApplicationRouter {

    private final ApplyForJobHandler           applyForJobHandler;
    private final GetApplicationsHandler       getApplicationsHandler;
    private final GetApplicationByIdHandler    getApplicationByIdHandler;
    private final UpdateApplicationStatusHandler updateApplicationStatusHandler;
    private final ExportApplicationsHandler    exportApplicationsHandler;

    public ApplicationRouter(MongoClient mongoClient) {
        ApplicationRepository repository = new ApplicationRepository(mongoClient);
        ApplicationService    service    = new ApplicationService(repository);

        this.applyForJobHandler             = new ApplyForJobHandler(service);
        this.getApplicationsHandler         = new GetApplicationsHandler(service);
        this.getApplicationByIdHandler      = new GetApplicationByIdHandler(service);
        this.updateApplicationStatusHandler = new UpdateApplicationStatusHandler(service);
        this.exportApplicationsHandler      = new ExportApplicationsHandler(service);
    }

    /**
     * Mount all application routes onto the provided sub-router.
     *
     * Typical usage in your MainVerticle:
     * <pre>
     *   Router api = Router.router(vertx);
     *   new ApplicationRouter(mongoClient).mount(api);
     *   mainRouter.mountSubRouter("/api", api);
     * </pre>
     *
     * ┌─────────────────────────────────────────────────────────────────────────┐
     * │  Method  │ Path                                    │ Handler             │
     * ├──────────┼─────────────────────────────────────────┼─────────────────────┤
     * │ POST     │ /applications                           │ ApplyForJob         │
     * │ GET      │ /applications                           │ GetApplications     │
     * │ GET      │ /applications/export                    │ ExportApplications  │
     * │ GET      │ /applications/:applicationId            │ GetApplicationById  │
     * │ PATCH    │ /applications/:applicationId/status     │ UpdateStatus        │
     * └──────────┴─────────────────────────────────────────┴─────────────────────┘
     *
     * NOTE: /applications/export is registered BEFORE /:applicationId so that
     *       "export" is not mistakenly captured as an applicationId path param.
     */
    public void mount(Router router) {
        router.route("/applications*").handler(BodyHandler.create());

        router.post("/applications")
                .handler(applyForJobHandler::handle);

        router.get("/applications")
                .handler(getApplicationsHandler::handle);

        // Static sub-path must be registered before the parameterised one
        router.get("/applications/export")
                .handler(exportApplicationsHandler::handle);

        router.get("/applications/:applicationId")
                .handler(getApplicationByIdHandler::handle);

        router.patch("/applications/:applicationId/status")
                .handler(updateApplicationStatusHandler::handle);
    }
}

