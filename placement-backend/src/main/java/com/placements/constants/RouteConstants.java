package com.placements.constants;

/**
 * HTTP route path constants.
 *
 * <p>Defines every path segment and composed full path used across routers and
 * handlers, eliminating duplicated inline strings and making base-path changes
 * a single-line update.
 *
 * <h3>Structure</h3>
 * <pre>
 *  BASE          /api/v1
 *  ├── STUDENTS  /api/v1/students
 *  ├── BATCHES   /api/v1/batches
 *  ├── COMPANIES /api/v1/companies
 *  ├── PLACEMENTS /api/v1/placements
 *  │     └── JOBS          /api/v1/placements/:id/jobs
 *  │           └── FIELDS  /api/v1/placements/:id/jobs/:jobId/fields
 *  ├── APPLICATIONS /api/v1/applications
 *  └── DASHBOARD    /api/v1/dashboard
 * </pre>
 */
public final class RouteConstants {

    private RouteConstants() {}

    // ── API root ──────────────────────────────────────────────────────────────
    public static final String BASE = "/api/v1";

    // ── Path segments (used for sub-router relative paths) ────────────────────
    public static final String SEGMENT_STUDENTS     = "/students";
    public static final String SEGMENT_BATCHES      = "/batches";
    public static final String SEGMENT_COMPANIES    = "/companies";
    public static final String SEGMENT_PLACEMENTS   = "/placements";
    public static final String SEGMENT_APPLICATIONS = "/applications";
    public static final String SEGMENT_DASHBOARD    = "/dashboard";
    public static final String SEGMENT_JOBS         = "/jobs";
    public static final String SEGMENT_FIELDS       = "/fields";
    public static final String SEGMENT_STATUS       = "/status";
    public static final String SEGMENT_EXPORT       = "/export";
    public static final String SEGMENT_SUMMARY      = "/summary";

    // ── Path parameters ───────────────────────────────────────────────────────
    public static final String PARAM_ID             = "/:id";
    public static final String PARAM_JOB_ID         = "/:jobId";
    public static final String PARAM_FIELD_ID       = "/:fieldId";
    public static final String PARAM_APPLICATION_ID = "/:applicationId";

    // ── Full base paths (used in RouterConfig for mountSubRouter) ─────────────
    public static final String STUDENTS_BASE     = BASE + SEGMENT_STUDENTS;
    public static final String BATCHES_BASE      = BASE + SEGMENT_BATCHES;
    public static final String COMPANIES_BASE    = BASE + SEGMENT_COMPANIES;
    public static final String PLACEMENTS_BASE   = BASE + SEGMENT_PLACEMENTS;
    public static final String APPLICATIONS_BASE = BASE + SEGMENT_APPLICATIONS;
    public static final String DASHBOARD_BASE    = BASE + SEGMENT_DASHBOARD;

    // ── Student routes ────────────────────────────────────────────────────────
    public static final String STUDENTS_GET_ALL  = SEGMENT_STUDENTS;
    public static final String STUDENTS_GET_BY_ID = SEGMENT_STUDENTS + PARAM_ID;
    public static final String STUDENTS_CREATE   = SEGMENT_STUDENTS;
    public static final String STUDENTS_UPDATE   = SEGMENT_STUDENTS + PARAM_ID;
    public static final String STUDENTS_DELETE   = SEGMENT_STUDENTS + PARAM_ID;

    // ── Batch routes ──────────────────────────────────────────────────────────
    public static final String BATCHES_GET_ALL   = SEGMENT_BATCHES;
    public static final String BATCHES_GET_BY_ID = SEGMENT_BATCHES + PARAM_ID;
    public static final String BATCHES_CREATE    = SEGMENT_BATCHES;
    public static final String BATCHES_UPDATE    = SEGMENT_BATCHES + PARAM_ID;
    public static final String BATCHES_DELETE    = SEGMENT_BATCHES + PARAM_ID;

    // ── Company routes ────────────────────────────────────────────────────────
    public static final String COMPANIES_GET_ALL   = SEGMENT_COMPANIES;
    public static final String COMPANIES_GET_BY_ID = SEGMENT_COMPANIES + PARAM_ID;
    public static final String COMPANIES_CREATE    = SEGMENT_COMPANIES;
    public static final String COMPANIES_UPDATE    = SEGMENT_COMPANIES + PARAM_ID;
    public static final String COMPANIES_DELETE    = SEGMENT_COMPANIES + PARAM_ID;

    // ── Placement routes ──────────────────────────────────────────────────────
    public static final String PLACEMENTS_GET_ALL   = SEGMENT_PLACEMENTS;
    public static final String PLACEMENTS_GET_BY_ID = SEGMENT_PLACEMENTS + PARAM_ID;
    public static final String PLACEMENTS_CREATE    = SEGMENT_PLACEMENTS;
    public static final String PLACEMENTS_UPDATE    = SEGMENT_PLACEMENTS + PARAM_ID;
    public static final String PLACEMENTS_DELETE    = SEGMENT_PLACEMENTS + PARAM_ID;

    // ── Job routes (relative to /placements/:id) ──────────────────────────────
    public static final String JOBS_ADD    = SEGMENT_PLACEMENTS + PARAM_ID + SEGMENT_JOBS;
    public static final String JOBS_UPDATE = SEGMENT_PLACEMENTS + PARAM_ID + SEGMENT_JOBS + PARAM_JOB_ID;
    public static final String JOBS_DELETE = SEGMENT_PLACEMENTS + PARAM_ID + SEGMENT_JOBS + PARAM_JOB_ID;

    // ── Job Field routes (relative to /placements/:id/jobs/:jobId) ────────────
    public static final String JOB_FIELDS_ADD    = SEGMENT_PLACEMENTS + PARAM_ID + SEGMENT_JOBS + PARAM_JOB_ID + SEGMENT_FIELDS;
    public static final String JOB_FIELDS_UPDATE = SEGMENT_PLACEMENTS + PARAM_ID + SEGMENT_JOBS + PARAM_JOB_ID + SEGMENT_FIELDS + PARAM_FIELD_ID;
    public static final String JOB_FIELDS_DELETE = SEGMENT_PLACEMENTS + PARAM_ID + SEGMENT_JOBS + PARAM_JOB_ID + SEGMENT_FIELDS + PARAM_FIELD_ID;

    // ── Application routes ────────────────────────────────────────────────────
    public static final String APPLICATIONS_GET_ALL   = SEGMENT_APPLICATIONS;
    public static final String APPLICATIONS_CREATE    = SEGMENT_APPLICATIONS;
    public static final String APPLICATIONS_EXPORT    = SEGMENT_APPLICATIONS + SEGMENT_EXPORT;
    public static final String APPLICATIONS_GET_BY_ID = SEGMENT_APPLICATIONS + PARAM_APPLICATION_ID;
    public static final String APPLICATIONS_UPDATE_STATUS = SEGMENT_APPLICATIONS + PARAM_APPLICATION_ID + SEGMENT_STATUS;

    // ── Dashboard routes ──────────────────────────────────────────────────────
    public static final String DASHBOARD_SUMMARY           = SEGMENT_SUMMARY;
    public static final String DASHBOARD_PLACEMENT_STATS   = "/placement-stats";
    public static final String DASHBOARD_RECENT_APPLICATIONS = "/recent-applications";
}

