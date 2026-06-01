package com.placements.handler.dashboard;

import com.placements.dto.response.DashboardResponse;
import com.placements.service.DashboardService;
import com.placements.util.ResponseUtil;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Handles {@code GET /api/v1/dashboard/summary}.
 *
 * <p>Delegates to {@link DashboardService#getSummary()} and wraps the
 * resulting {@link com.placements.model.DashboardStats} in a
 * {@link DashboardResponse} before writing it to the response.
 */
public class GetSummaryHandler implements Handler<RoutingContext> {

    private final DashboardService dashboardService;

    /**
     * @param dashboardService service that computes summary statistics
     */
    public GetSummaryHandler(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Override
    public void handle(RoutingContext ctx) {
        dashboardService.getSummary()
                .onSuccess(stats ->
                        ResponseUtil.ok(ctx, "Dashboard summary fetched successfully",
                                DashboardResponse.ofStats(stats)))
                .onFailure(err -> {
                    System.err.println("[GetSummaryHandler] Failed to fetch summary: " + err.getMessage());
                    ResponseUtil.internalError(ctx, err);
                });
    }
}

