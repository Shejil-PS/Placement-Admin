package com.placements.model;

/**
 * Aggregated counts across every core collection.
 * Populated by {@code DashboardService#getSummary()} and serialised
 * directly into the API response body via Jackson / Vert.x Json.
 */
public class DashboardStats {

    private long totalStudents;
    private long totalBatches;
    private long totalCompanies;
    private long totalPlacements;
    private long totalApplications;

    // ── Constructors ─────────────────────────────────────────────────────────

    public DashboardStats() {}

    public DashboardStats(long totalStudents,
                          long totalBatches,
                          long totalCompanies,
                          long totalPlacements,
                          long totalApplications) {
        this.totalStudents     = totalStudents;
        this.totalBatches      = totalBatches;
        this.totalCompanies    = totalCompanies;
        this.totalPlacements   = totalPlacements;
        this.totalApplications = totalApplications;
    }

    // ── Accessors ────────────────────────────────────────────────────────────

    public long getTotalStudents()     { return totalStudents; }
    public long getTotalBatches()      { return totalBatches; }
    public long getTotalCompanies()    { return totalCompanies; }
    public long getTotalPlacements()   { return totalPlacements; }
    public long getTotalApplications() { return totalApplications; }

    public void setTotalStudents(long totalStudents)         { this.totalStudents = totalStudents; }
    public void setTotalBatches(long totalBatches)           { this.totalBatches = totalBatches; }
    public void setTotalCompanies(long totalCompanies)       { this.totalCompanies = totalCompanies; }
    public void setTotalPlacements(long totalPlacements)     { this.totalPlacements = totalPlacements; }
    public void setTotalApplications(long totalApplications) { this.totalApplications = totalApplications; }

    @Override
    public String toString() {
        return "DashboardStats{"
                + "totalStudents="     + totalStudents
                + ", totalBatches="    + totalBatches
                + ", totalCompanies="  + totalCompanies
                + ", totalPlacements=" + totalPlacements
                + ", totalApplications=" + totalApplications
                + '}';
    }
}
