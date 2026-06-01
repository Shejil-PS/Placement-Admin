package com.placements.config;

import com.placements.repository.ApplicationRepository;
import com.placements.repository.BatchRepository;
import com.placements.repository.CompanyRepository;
import com.placements.repository.PlacementRepository;
import com.placements.repository.StudentRepository;
import com.placements.router.ApplicationRouter;
import com.placements.router.BatchRouter;
import com.placements.router.CompanyRouter;
import com.placements.router.DashboardRouter;
import com.placements.router.PlacementRouter;
import com.placements.router.StudentRouter;
import com.placements.service.DashboardService;
import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;

/**
 * Central routing configuration for the Placement Management System.
 *
 * <p>Constructs a single root {@link Router} and mounts every module onto it,
 * normalising the three different constructor/mount conventions used across the
 * existing routers:
 *
 * <pre>
 *  Convention A – constructor takes (Router, MongoClient) and self-registers:
 *    StudentRouter, BatchRouter, CompanyRouter
 *
 *  Convention B – constructor takes (MongoClient), routes registered via .mount(Router):
 *    PlacementRouter, ApplicationRouter
 *
 *  Convention C – constructor takes (Vertx, DashboardService), returns sub-router via .router():
 *    DashboardRouter
 * </pre>
 *
 * <p>Usage in {@code HttpServerVerticle}:
 * <pre>{@code
 *   Router root = RouterConfig.build(vertx, mongoClient);
 *   vertx.createHttpServer().requestHandler(root).listen(port);
 * }</pre>
 */
public final class RouterConfig {

    private RouterConfig() {}

    /**
     * Builds and returns the fully configured root {@link Router}.
     *
     * <p>Global middleware ({@link BodyHandler}, {@link LoggerHandler}) is
     * applied once here so individual routers do not need to repeat it.
     * Each module's routes are then wired according to its own convention.
     *
     * @param vertx       the Vert.x instance
     * @param mongoClient the shared MongoDB client
     * @return the root router ready to pass to {@code HttpServer#requestHandler}
     */
    public static Router build(Vertx vertx, MongoClient mongoClient) {
        Router root = Router.router(vertx);

        // ── Global middleware ──────────────────────────────────────────────────
        root.route().handler(LoggerHandler.create());
        root.route().handler(BodyHandler.create());

        // ── Convention A: constructor self-registers routes on the router ──────
        // StudentRouter, BatchRouter, and CompanyRouter each accept the root
        // router directly and register their own paths during construction.
        new StudentRouter(root, mongoClient);
        new BatchRouter(root, mongoClient);
        new CompanyRouter(root, mongoClient);

        // ── Convention B: separate .mount(Router) call ─────────────────────────
        // PlacementRouter and ApplicationRouter build their handlers in the
        // constructor, then register routes when mount() is called.
        new PlacementRouter(mongoClient).mount(root);
        new ApplicationRouter(mongoClient).mount(root);

        // ── Convention C: sub-router returned by .router() ────────────────────
        // DashboardRouter needs its own service graph (all five repositories +
        // a raw MongoClient for aggregation) assembled here before mounting.
        DashboardService dashboardService = buildDashboardService(mongoClient);
        Router dashboardSubRouter = new DashboardRouter(vertx, dashboardService).router();
        root.route("/api/v1/dashboard*").subRouter(dashboardSubRouter);

        // ── 404 fallback ───────────────────────────────────────────────────────
        root.route().handler(ctx ->
                ctx.response()
                   .setStatusCode(404)
                   .putHeader("Content-Type", "application/json")
                   .end("{\"success\":false,\"message\":\"Route not found: "
                           + ctx.request().path() + "\"}"));

        return root;
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * Assembles the {@link DashboardService} by constructing each repository
     * the service depends on from the shared {@link MongoClient}.
     *
     * @param mongoClient the shared MongoDB client
     * @return a fully initialised {@link DashboardService}
     */
    private static DashboardService buildDashboardService(MongoClient mongoClient) {
        return new DashboardService(
                new StudentRepository(mongoClient),
                new BatchRepository(mongoClient),
                new CompanyRepository(mongoClient),
                new PlacementRepository(mongoClient),
                new ApplicationRepository(mongoClient),
                mongoClient
        );
    }
}

