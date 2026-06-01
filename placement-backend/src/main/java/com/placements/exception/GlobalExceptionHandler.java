package com.placements.handler;

import com.placements.exception.ResourceNotFoundException;
import com.placements.exception.ValidationException;
import com.placements.model.ApiResponse;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * Centralised failure handler for the Vert.x HTTP server.
 *
 * <p>Wire this handler as the <em>last</em> route on the root router so it
 * catches every unhandled exception forwarded by {@code ctx.fail(Throwable)}
 * or by Vert.x itself (e.g. body-decode errors, route timeouts):
 *
 * <pre>{@code
 * // in RouterConfig.build() — after all module routes:
 * root.route().failureHandler(new GlobalExceptionHandler());
 * }</pre>
 *
 * <h3>Exception → HTTP status mapping</h3>
 * <pre>
 *  ValidationException          →  400 Bad Request
 *  ResourceNotFoundException    →  404 Not Found
 *  IllegalArgumentException     →  400 Bad Request
 *  IllegalStateException        →  409 Conflict
 *  UnsupportedOperationException→  501 Not Implemented
 *  Any other Throwable          →  500 Internal Server Error
 * </pre>
 *
 * <h3>Response format</h3>
 * Every response is a JSON {@link ApiResponse} envelope:
 * <pre>{@code
 * // Simple error
 * { "success": false, "message": "Student not found with id: abc" }
 *
 * // ValidationException with field violations
 * {
 *   "success": false,
 *   "message": "Request validation failed",
 *   "data": {
 *     "violations": ["name: must not be blank", "email: must be a valid email"]
 *   }
 * }
 * }</pre>
 */
public class GlobalExceptionHandler implements Handler<RoutingContext> {

    private static final String CONTENT_TYPE_JSON = "application/json";

    @Override
    public void handle(RoutingContext ctx) {
        Throwable failure = ctx.failure();
        int       statusCode;
        String    message;
        JsonObject data = null;

        // ── Classify the failure ──────────────────────────────────────────────

        if (failure instanceof ValidationException ve) {
            statusCode = 400;
            message    = ve.getMessage();
            if (ve.hasViolations()) {
                JsonArray violations = new JsonArray();
                ve.getViolations().forEach(violations::add);
                data = new JsonObject().put("violations", violations);
            }

        } else if (failure instanceof ResourceNotFoundException rnfe) {
            statusCode = 404;
            message    = rnfe.getMessage();

        } else if (failure instanceof IllegalArgumentException iae) {
            statusCode = 400;
            message    = iae.getMessage() != null
                    ? iae.getMessage()
                    : "Invalid argument";

        } else if (failure instanceof IllegalStateException ise) {
            statusCode = 409;
            message    = ise.getMessage() != null
                    ? ise.getMessage()
                    : "Conflicting state";

        } else if (failure instanceof UnsupportedOperationException uoe) {
            statusCode = 501;
            message    = uoe.getMessage() != null
                    ? uoe.getMessage()
                    : "Operation not supported";

        } else if (ctx.statusCode() >= 400) {
            // Vert.x set a status code without an attached exception
            // (e.g. 405 Method Not Allowed, 413 Payload Too Large).
            statusCode = ctx.statusCode();
            message    = resolveHttpStatusMessage(statusCode);

        } else if (failure != null) {
            // Unknown runtime exception — log it, hide details from caller.
            statusCode = 500;
            message    = "An unexpected error occurred";
            logInternalError(ctx, failure);

        } else {
            statusCode = 500;
            message    = "An unexpected error occurred";
        }

        // ── Log non-500 failures at warn level ────────────────────────────────
        if (statusCode < 500 && failure != null) {
            System.err.printf("[GlobalExceptionHandler] %d %s — %s%n",
                    statusCode,
                    failure.getClass().getSimpleName(),
                    failure.getMessage());
        }

        // ── Build ApiResponse body ────────────────────────────────────────────
        ApiResponse<?> body = (data != null)
                ? ApiResponse.error(message, data)
                : ApiResponse.error(message);

        writeResponse(ctx, statusCode, body);
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    /**
     * Serialises the {@link ApiResponse} to JSON and ends the HTTP response.
     * Guards against double-end in case a previous handler partially wrote.
     */
    private void writeResponse(RoutingContext ctx, int statusCode, ApiResponse<?> body) {
        if (ctx.response().ended()) {
            return;
        }
        ctx.response()
           .setStatusCode(statusCode)
           .putHeader("Content-Type", CONTENT_TYPE_JSON)
           .end(JsonObject.mapFrom(body).encode());
    }

    /**
     * Logs a full stack trace for unexpected 500-level errors without leaking
     * internals to the HTTP caller.
     */
    private void logInternalError(RoutingContext ctx, Throwable failure) {
        System.err.printf("[GlobalExceptionHandler] 500 INTERNAL ERROR — %s %s%n",
                ctx.request().method(), ctx.request().path());
        System.err.println("Caused by: " + failure.getMessage());
        failure.printStackTrace(System.err);
    }

    /**
     * Returns a plain-English status message for HTTP error codes generated
     * by Vert.x itself (no attached {@code Throwable}).
     */
    private String resolveHttpStatusMessage(int statusCode) {
        return switch (statusCode) {
            case 400 -> "Bad request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Resource not found";
            case 405 -> "Method not allowed";
            case 408 -> "Request timeout";
            case 409 -> "Conflict";
            case 413 -> "Payload too large";
            case 415 -> "Unsupported media type";
            case 422 -> "Unprocessable entity";
            case 429 -> "Too many requests";
            case 501 -> "Not implemented";
            case 503 -> "Service unavailable";
            default  -> "An error occurred";
        };
    }
}

