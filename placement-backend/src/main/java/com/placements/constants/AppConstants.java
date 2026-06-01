package com.placements.constants;

/**
 * General application-wide constants.
 *
 * <p>Covers HTTP headers, content types, pagination defaults, date formats,
 * and field key names shared across models, handlers, and services.
 */
public final class AppConstants {

    private AppConstants() {}

    // ── HTTP ──────────────────────────────────────────────────────────────────
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HEADER_CONTENT_TYPE  = "Content-Type";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String BEARER_PREFIX        = "Bearer ";

    // ── Pagination ────────────────────────────────────────────────────────────
    public static final int    DEFAULT_PAGE      = 1;
    public static final int    DEFAULT_PAGE_SIZE = 10;
    public static final int    MAX_PAGE_SIZE     = 100;
    public static final String QUERY_PARAM_PAGE  = "page";
    public static final String QUERY_PARAM_SIZE  = "size";
    public static final String QUERY_PARAM_SORT  = "sort";

    // ── MongoDB common field names ────────────────────────────────────────────
    public static final String FIELD_ID         = "_id";
    public static final String FIELD_CREATED_AT = "createdAt";
    public static final String FIELD_UPDATED_AT = "updatedAt";

    // ── Dashboard ─────────────────────────────────────────────────────────────
    /** Maximum number of documents returned by the recent-applications query. */
    public static final int DASHBOARD_RECENT_APPS_LIMIT = 10;

    // ── Application status values ─────────────────────────────────────────────
    public static final String STATUS_APPLIED   = "Applied";
    public static final String STATUS_SHORTLISTED = "Shortlisted";
    public static final String STATUS_SELECTED  = "Selected";
    public static final String STATUS_REJECTED  = "Rejected";
    public static final String STATUS_WITHDRAWN = "Withdrawn";
}

