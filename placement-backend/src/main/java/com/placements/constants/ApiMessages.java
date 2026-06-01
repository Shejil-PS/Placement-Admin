package com.placements.constants;

/**
 * Centralised API response message strings.
 *
 * <p>Keeps every user-facing message in one place so wording is consistent
 * across handlers and easy to update without hunting through the codebase.
 *
 * <p>Naming convention: {@code <RESOURCE>_<VERB>_SUCCESS} / {@code <RESOURCE>_NOT_FOUND} etc.
 *
 * <pre>{@code
 * ResponseUtil.ok(ctx, ApiMessages.STUDENT_FETCH_SUCCESS, student);
 * ResponseUtil.notFound(ctx, ApiMessages.STUDENT_NOT_FOUND);
 * throw new ResourceNotFoundException(ApiMessages.STUDENT_NOT_FOUND);
 * }</pre>
 */
public final class ApiMessages {

    private ApiMessages() {}

    // ── Student ───────────────────────────────────────────────────────────────
    public static final String STUDENTS_FETCH_SUCCESS  = "Students fetched successfully";
    public static final String STUDENT_FETCH_SUCCESS   = "Student fetched successfully";
    public static final String STUDENT_CREATE_SUCCESS  = "Student created successfully";
    public static final String STUDENT_UPDATE_SUCCESS  = "Student updated successfully";
    public static final String STUDENT_DELETE_SUCCESS  = "Student deleted successfully";
    public static final String STUDENT_NOT_FOUND       = "Student not found";

    // ── Batch ─────────────────────────────────────────────────────────────────
    public static final String BATCHES_FETCH_SUCCESS   = "Batches fetched successfully";
    public static final String BATCH_FETCH_SUCCESS     = "Batch fetched successfully";
    public static final String BATCH_CREATE_SUCCESS    = "Batch created successfully";
    public static final String BATCH_UPDATE_SUCCESS    = "Batch updated successfully";
    public static final String BATCH_DELETE_SUCCESS    = "Batch deleted successfully";
    public static final String BATCH_NOT_FOUND         = "Batch not found";

    // ── Company ───────────────────────────────────────────────────────────────
    public static final String COMPANIES_FETCH_SUCCESS = "Companies fetched successfully";
    public static final String COMPANY_FETCH_SUCCESS   = "Company fetched successfully";
    public static final String COMPANY_CREATE_SUCCESS  = "Company created successfully";
    public static final String COMPANY_UPDATE_SUCCESS  = "Company updated successfully";
    public static final String COMPANY_DELETE_SUCCESS  = "Company deleted successfully";
    public static final String COMPANY_NOT_FOUND       = "Company not found";

    // ── Placement ─────────────────────────────────────────────────────────────
    public static final String PLACEMENTS_FETCH_SUCCESS = "Placements fetched successfully";
    public static final String PLACEMENT_FETCH_SUCCESS  = "Placement fetched successfully";
    public static final String PLACEMENT_CREATE_SUCCESS = "Placement created successfully";
    public static final String PLACEMENT_UPDATE_SUCCESS = "Placement updated successfully";
    public static final String PLACEMENT_DELETE_SUCCESS = "Placement deleted successfully";
    public static final String PLACEMENT_NOT_FOUND      = "Placement not found";

    // ── Job (nested under Placement) ──────────────────────────────────────────
    public static final String JOB_ADD_SUCCESS          = "Job added successfully";
    public static final String JOB_UPDATE_SUCCESS       = "Job updated successfully";
    public static final String JOB_DELETE_SUCCESS       = "Job deleted successfully";
    public static final String JOB_NOT_FOUND            = "Job not found";

    // ── Job Field (nested under Job) ──────────────────────────────────────────
    public static final String JOB_FIELD_ADD_SUCCESS    = "Job field added successfully";
    public static final String JOB_FIELD_UPDATE_SUCCESS = "Job field updated successfully";
    public static final String JOB_FIELD_DELETE_SUCCESS = "Job field deleted successfully";
    public static final String JOB_FIELD_NOT_FOUND      = "Job field not found";

    // ── Application ───────────────────────────────────────────────────────────
    public static final String APPLICATIONS_FETCH_SUCCESS  = "Applications fetched successfully";
    public static final String APPLICATION_FETCH_SUCCESS   = "Application fetched successfully";
    public static final String APPLICATION_APPLY_SUCCESS   = "Application submitted successfully";
    public static final String APPLICATION_STATUS_SUCCESS  = "Application status updated successfully";
    public static final String APPLICATION_DELETE_SUCCESS  = "Application deleted successfully";
    public static final String APPLICATION_EXPORT_SUCCESS  = "Applications exported successfully";
    public static final String APPLICATION_NOT_FOUND       = "Application not found";

    // ── Dashboard ─────────────────────────────────────────────────────────────
    public static final String DASHBOARD_SUMMARY_SUCCESS      = "Dashboard summary fetched successfully";
    public static final String DASHBOARD_PLACEMENT_STATS_SUCCESS = "Placement statistics fetched successfully";
    public static final String DASHBOARD_RECENT_APPS_SUCCESS  = "Recent applications fetched successfully";

    // ── Generic ───────────────────────────────────────────────────────────────
    public static final String INTERNAL_SERVER_ERROR   = "An unexpected error occurred";
    public static final String VALIDATION_FAILED       = "Request validation failed";
    public static final String INVALID_ID              = "Invalid or missing ID parameter";
    public static final String INVALID_REQUEST_BODY    = "Invalid or missing request body";
}

