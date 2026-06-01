package com.placements.router;

import com.placements.handler.dashboard.GetPlacementStatsHandler;
import com.placements.handler.dashboard.GetRecentActivityHandler;
import com.placements.handler.dashboard.GetSummaryHandler;
import com.placements.service.DashboardService;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * Registers all dashboard-related routes on a sub-router that is mounted at
 * {@code /api/v1/dashboard} by {@code HttpServerVerticle}.
 *
 * <p>Route table:
 * <pre>
 *  GET /                      → 302 redirect to /summary  (convenience)
 *  GET /summary               → {@link GetSummaryHandler}
 *  GET /placement-stats       → {@link GetPlacementStatsHandler}
 *  GET /recent-applications   → {@link GetRecentActivityHandler}
 * </pre>
 *
 * <p>Because this router is mounted as a sub-router, all paths here are
 * <em>relative</em> — no {@code /api/v1/dashboard} prefix is needed.
 */
public class DashboardRouter {

    private final Router          router;
    private final DashboardService dashboardService;

    /**
     * Builds the router and wires all handlers.
     *
     * @param vertx           Vert.x instance (required to create a {@link Router})
     * @param dashboardService shared service instance injected into every handler
     */
    public DashboardRouter(Vertx vertx, DashboardService dashboardService) {
        this.router           = Router.router(vertx);
        this.dashboardService = dashboardService;
        registerRoutes();
    }

    /**
     * Returns the configured sub-router for mounting in {@code HttpServerVerticle}.
     *
     * @return the Vert.x {@link Router} for this module
     */
    public Router router() {
        return router;
    }

    // ── Route registration ────────────────────────────────────────────────────

    private void registerRoutes() {
        // Summary — aggregated collection counts
        router.get("/summary")
              .handler(new GetSummaryHandler(dashboardService));

        // Placement stats — per-placement application counts & status breakdown
        router.get("/placement-stats")
              .handler(new GetPlacementStatsHandler(dashboardService));

        // Recent activity — 10 most recent applications
        router.get("/recent-applications")
              .handler(new GetRecentActivityHandler(dashboardService));
    }
}

