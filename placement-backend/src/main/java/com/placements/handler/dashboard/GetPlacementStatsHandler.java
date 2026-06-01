package com.placements.handler.dashboard;

import com.placements.dto.response.DashboardResponse;
import com.placements.service.DashboardService;
import com.placements.util.ResponseUtil;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Handles {@code GET /api/v1/dashboard/placement-stats}.
 *
 * <p>Returns per-placement application counts and status breakdowns produced
 * by a MongoDB aggregation pipeline in
 * {@link DashboardService#getPlacementStats()}.
 */
public class GetPlacementStatsHandler implements Handler<RoutingContext> {

    private final DashboardService dashboardService;

    /**
     * @param dashboardService service that executes the placement aggregation
     */
    public GetPlacementStatsHandler(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Override
    public void handle(RoutingContext ctx) {
        dashboardService.getPlacementStats()
                .onSuccess(stats -> {
                    if (stats.isEmpty()) {
                        ResponseUtil.ok(ctx, "No placement statistics available",
                                DashboardResponse.ofPlacementStats(stats));
                    } else {
                        ResponseUtil.ok(ctx, "Placement statistics fetched successfully",
                                DashboardResponse.ofPlacementStats(stats));
                    }
                })
                .onFailure(err -> {
                    System.err.println("[GetPlacementStatsHandler] Failed to fetch placement stats: "
                            + err.getMessage());
                    ResponseUtil.internalError(ctx, err);
                });
    }
}

