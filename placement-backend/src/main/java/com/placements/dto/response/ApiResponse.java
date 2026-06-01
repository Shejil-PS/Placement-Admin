package com.placements.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Standard API response wrapper.
 *
 * @param <T> the type of the response data payload
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ApiResponse<T> {

    private final boolean success;
    private final String message;
    private final T data;

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // ── Factory methods ──────────────────────────────────────────────────────

    /** 200-level success with data and a custom message. */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    /** 200-level success with data and a default "OK" message. */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "OK", data);
    }

    /** 200-level success with no data payload (e.g. DELETE, logout). */
    public static <Void> ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, message, null);
    }

    /** Error response with a human-readable message and no data. */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    /** Error response with a message and optional debug/error data. */
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }

    // ── Accessors ────────────────────────────────────────────────────────────

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ApiResponse{success=" + success
                + ", message='" + message + '\''
                + ", data=" + data + '}';
    }
}
