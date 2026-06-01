package com.placements.exception;

/**
 * Thrown when a requested resource cannot be found in the data store.
 *
 * <p>Maps to HTTP 404 in {@link com.placements.handler.GlobalExceptionHandler}.
 *
 * <p>Examples:
 * <pre>{@code
 * // Generic not-found
 * throw new ResourceNotFoundException("Student not found");
 *
 * // Resource type + id (recommended — makes logs easy to search)
 * throw new ResourceNotFoundException("Student", studentId);
 *
 * // Nested resource
 * throw new ResourceNotFoundException("Job", jobId,
 *     "within Placement " + placementId);
 * }</pre>
 */
public class ResourceNotFoundException extends RuntimeException {

    /** The logical type of the missing resource (e.g. {@code "Student"}). */
    private final String resourceType;

    /** The identifier that was looked up (e.g. an id string). */
    private final String resourceId;

    // ── Constructors ──────────────────────────────────────────────────────────

    /**
     * Free-form message constructor — use when a type+id breakdown is not
     * applicable or practical.
     *
     * @param message human-readable description of what was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
        this.resourceType = null;
        this.resourceId   = null;
    }

    /**
     * Structured constructor that builds the message automatically.
     * Produces: {@code "Student not found with id: abc123"}
     *
     * @param resourceType logical name of the entity type (e.g. {@code "Student"})
     * @param resourceId   the identifier that could not be resolved
     */
    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(resourceType + " not found with id: " + resourceId);
        this.resourceType = resourceType;
        this.resourceId   = resourceId;
    }

    /**
     * Structured constructor with additional context (e.g. a parent resource).
     * Produces: {@code "Job not found with id: j1 within Placement p9"}
     *
     * @param resourceType logical name of the entity type
     * @param resourceId   the identifier that could not be resolved
     * @param context      extra context appended to the message
     */
    public ResourceNotFoundException(String resourceType, String resourceId, String context) {
        super(resourceType + " not found with id: " + resourceId + " " + context);
        this.resourceType = resourceType;
        this.resourceId   = resourceId;
    }

    /**
     * Wraps an underlying cause.
     *
     * @param message human-readable description
     * @param cause   original exception
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.resourceType = null;
        this.resourceId   = null;
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    /**
     * Returns the logical resource type, or {@code null} when the free-form
     * constructor was used.
     *
     * @return resource type string (e.g. {@code "Student"}), may be {@code null}
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * Returns the resource identifier that triggered this exception, or
     * {@code null} when the free-form constructor was used.
     *
     * @return resource id string, may be {@code null}
     */
    public String getResourceId() {
        return resourceId;
    }
}

