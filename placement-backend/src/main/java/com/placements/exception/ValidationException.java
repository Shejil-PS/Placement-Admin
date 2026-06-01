package com.placements.exception;

import java.util.Collections;
import java.util.List;

/**
 * Thrown when incoming request data fails validation rules.
 *
 * <p>Carries an optional list of field-level violation messages so the API
 * can return a structured 400 response that tells the caller exactly which
 * fields are invalid and why.
 *
 * <p>Examples:
 * <pre>{@code
 * // Single message
 * throw new ValidationException("Email address is required");
 *
 * // Multiple field violations
 * throw new ValidationException("Request validation failed", List.of(
 *     "name: must not be blank",
 *     "email: must be a valid email address",
 *     "cgpa: must be between 0.0 and 10.0"
 * ));
 * }</pre>
 */
public class ValidationException extends RuntimeException {

    /** Field-level violation details; never {@code null}, may be empty. */
    private final List<String> violations;

    // ── Constructors ──────────────────────────────────────────────────────────

    /**
     * Single-message validation failure (no field-level detail).
     *
     * @param message human-readable summary of what failed
     */
    public ValidationException(String message) {
        super(message);
        this.violations = Collections.emptyList();
    }

    /**
     * Validation failure with one or more field-level violation messages.
     *
     * @param message    top-level summary (used as the {@code ApiResponse.message})
     * @param violations per-field error strings (e.g. {@code "email: must not be blank"})
     */
    public ValidationException(String message, List<String> violations) {
        super(message);
        this.violations = violations != null ? List.copyOf(violations) : Collections.emptyList();
    }

    /**
     * Wraps an underlying cause alongside field violations.
     *
     * @param message    top-level summary
     * @param violations per-field error strings
     * @param cause      original exception
     */
    public ValidationException(String message, List<String> violations, Throwable cause) {
        super(message, cause);
        this.violations = violations != null ? List.copyOf(violations) : Collections.emptyList();
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    /**
     * Returns an unmodifiable list of field-level violation messages.
     * Empty when no per-field detail was provided.
     *
     * @return violation messages, never {@code null}
     */
    public List<String> getViolations() {
        return violations;
    }

    /** {@code true} when at least one field-level violation message is present. */
    public boolean hasViolations() {
        return !violations.isEmpty();
    }
}
