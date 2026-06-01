package com.placements.handler.dashboard;

import com.placements.dto.response.DashboardResponse;
import com.placements.service.DashboardService;
import com.placements.util.ResponseUtil;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Handles {@code GET /api/v1/dashboard/recent-applications}.
 *
 * <p>Returns the 10 most recently created applications, sorted by
 * {@code createdAt} descending, via
 * {@link DashboardService#getRecentApplications()}.
 */
public class GetRecentActivityHandler implements Handler<RoutingContext> {

    private final DashboardService dashboardService;

    /**
     * @param dashboardService service that queries recent applications
     */
    public GetRecentActivityHandler(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Override
    public void handle(RoutingContext ctx) {
        dashboardService.getRecentApplications()
                .onSuccess(applications -> {
                    if (applications.isEmpty()) {
                        ResponseUtil.ok(ctx, "No recent applications found",
                                DashboardResponse.ofRecentApplications(applications));
                    } else {
                        ResponseUtil.ok(ctx, "Recent applications fetched successfully",
                                DashboardResponse.ofRecentApplications(applications));
                    }
                })
                .onFailure(err -> {
                    System.err.println("[GetRecentActivityHandler] Failed to fetch recent applications: "
                            + err.getMessage());
                    ResponseUtil.internalError(ctx, err);
                });
    }
}

