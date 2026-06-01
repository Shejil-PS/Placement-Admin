package com.placements.router;

import com.placements.handler.placement.*;
import com.placements.repository.PlacementRepository;
import com.placements.service.PlacementService;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class PlacementRouter {

    private final PlacementService service;

    // Placement handlers
    private final GetPlacementsHandler getPlacementsHandler;
    private final GetPlacementByIdHandler getPlacementByIdHandler;
    private final CreatePlacementHandler createPlacementHandler;
    private final UpdatePlacementHandler updatePlacementHandler;
    private final DeletePlacementHandler deletePlacementHandler;

    // Job handlers
    private final AddJobHandler addJobHandler;
    private final UpdateJobHandler updateJobHandler;
    private final DeleteJobHandler deleteJobHandler;

    // Job Field handlers
    private final AddJobFieldHandler addJobFieldHandler;
    private final UpdateJobFieldHandler updateJobFieldHandler;
    private final DeleteJobFieldHandler deleteJobFieldHandler;

    public PlacementRouter(MongoClient mongoClient) {
        PlacementRepository repository = new PlacementRepository(mongoClient);
        this.service = new PlacementService(repository);

        this.getPlacementsHandler     = new GetPlacementsHandler(service);
        this.getPlacementByIdHandler  = new GetPlacementByIdHandler(service);
        this.createPlacementHandler   = new CreatePlacementHandler(service);
        this.updatePlacementHandler   = new UpdatePlacementHandler(service);
        this.deletePlacementHandler   = new DeletePlacementHandler(service);

        this.addJobHandler    = new AddJobHandler(service);
        this.updateJobHandler = new UpdateJobHandler(service);
        this.deleteJobHandler = new DeleteJobHandler(service);

        this.addJobFieldHandler    = new AddJobFieldHandler(service);
        this.updateJobFieldHandler = new UpdateJobFieldHandler(service);
        this.deleteJobFieldHandler = new DeleteJobFieldHandler(service);
    }

    /**
     * Mount all placement routes onto the provided sub-router.
     * Typical usage in MainVerticle:
     * <pre>
     *   Router api = Router.router(vertx);
     *   new PlacementRouter(mongoClient).mount(api);
     *   mainRouter.mountSubRouter("/api", api);
     * </pre>
     */
    public void mount(Router router) {
        router.route("/placements*").handler(BodyHandler.create());

        // ── Placement CRUD ──────────────────────────────────────────────────────
        router.get("/placements")           .handler(getPlacementsHandler::handle);
        router.get("/placements/:id")       .handler(getPlacementByIdHandler::handle);
        router.post("/placements")          .handler(createPlacementHandler::handle);
        router.put("/placements/:id")       .handler(updatePlacementHandler::handle);
        router.delete("/placements/:id")    .handler(deletePlacementHandler::handle);

        // ── Job CRUD ────────────────────────────────────────────────────────────
        router.post("/placements/:id/jobs")              .handler(addJobHandler::handle);
        router.put("/placements/:id/jobs/:jobId")        .handler(updateJobHandler::handle);
        router.delete("/placements/:id/jobs/:jobId")     .handler(deleteJobHandler::handle);

        // ── Job Field CRUD ──────────────────────────────────────────────────────
        router.post("/placements/:id/jobs/:jobId/fields")                    .handler(addJobFieldHandler::handle);
        router.put("/placements/:id/jobs/:jobId/fields/:fieldId")            .handler(updateJobFieldHandler::handle);
        router.delete("/placements/:id/jobs/:jobId/fields/:fieldId")         .handler(deleteJobFieldHandler::handle);
    }
}

