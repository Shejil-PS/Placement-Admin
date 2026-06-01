package com.placements.dto.response;

import com.placements.model.DashboardStats;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * Top-level DTO returned by every dashboard endpoint.
 *
 * <p>Each handler uses only the field(s) relevant to its endpoint;
 * {@code @JsonInclude(NON_NULL)} keeps unused fields out of the wire payload.
 *
 * <pre>
 *  GET /summary              → stats populated
 *  GET /placement-stats      → placementStats populated
 *  GET /recent-applications  → recentApplications populated
 * </pre>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardResponse {

    /** Populated by {@code GetSummaryHandler}. */
    private DashboardStats stats;

    /** Populated by {@code GetPlacementStatsHandler}. */
    private List<JsonObject> placementStats;

    /** Populated by {@code GetRecentActivityHandler}. */
    private List<JsonObject> recentApplications;

    // ── Constructors ─────────────────────────────────────────────────────────

    public DashboardResponse() {}

    // Named factory methods make call-sites self-documenting.

    /** Build a summary response. */
    public static DashboardResponse ofStats(DashboardStats stats) {
        DashboardResponse r = new DashboardResponse();
        r.stats = stats;
        return r;
    }

    /** Build a placement-stats response. */
    public static DashboardResponse ofPlacementStats(List<JsonObject> placementStats) {
        DashboardResponse r = new DashboardResponse();
        r.placementStats = placementStats;
        return r;
    }

    /** Build a recent-applications response. */
    public static DashboardResponse ofRecentApplications(List<JsonObject> recentApplications) {
        DashboardResponse r = new DashboardResponse();
        r.recentApplications = recentApplications;
        return r;
    }

    // ── Accessors ────────────────────────────────────────────────────────────

    public DashboardStats getStats()                          { return stats; }
    public List<JsonObject> getPlacementStats()               { return placementStats; }
    public List<JsonObject> getRecentApplications()           { return recentApplications; }

    public void setStats(DashboardStats stats)                        { this.stats = stats; }
    public void setPlacementStats(List<JsonObject> placementStats)    { this.placementStats = placementStats; }
    public void setRecentApplications(List<JsonObject> recentApplications) {
        this.recentApplications = recentApplications;
    }
}

