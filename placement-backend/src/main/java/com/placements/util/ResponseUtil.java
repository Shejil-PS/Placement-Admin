package com.placements.util;

import com.placements.model.ApiResponse;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 * Utility class for writing {@link ApiResponse} payloads to a Vert.x
 * {@link RoutingContext}.  All methods set {@code Content-Type: application/json}
 * and end the response, so handlers need no extra boilerplate.
 *
 * <p>Usage example:
 * <pre>{@code
 *  router.get("/users/:id").handler(ctx -> {
 *      User user = userService.findById(ctx.pathParam("id"));
 *      if (user == null) {
 *          ResponseUtil.notFound(ctx, "User not found");
 *      } else {
 *          ResponseUtil.ok(ctx, "User fetched", user);
 *      }
 *  });
 * }</pre>
 */
public final class ResponseUtil {

    private static final String CONTENT_TYPE = "application/json";

    private ResponseUtil() {}

    // ── 2xx ─────────────────────────────────────────────────────────────────

    /** 200 OK – success with data and a custom message. */
    public static <T> void ok(RoutingContext ctx, String message, T data) {
        send(ctx, 200, ApiResponse.success(message, data));
    }

    /** 200 OK – success with data and a default "OK" message. */
    public static <T> void ok(RoutingContext ctx, T data) {
        send(ctx, 200, ApiResponse.success(data));
    }

    /** 201 Created – resource created successfully. */
    public static <T> void created(RoutingContext ctx, String message, T data) {
        send(ctx, 201, ApiResponse.success(message, data));
    }

    /** 204 No Content – success with no body (e.g. DELETE). */
    public static void noContent(RoutingContext ctx) {
        ctx.response().setStatusCode(204).end();
    }

    // ── 4xx ─────────────────────────────────────────────────────────────────

    /** 400 Bad Request – invalid input from the caller. */
    public static void badRequest(RoutingContext ctx, String message) {
        send(ctx, 400, ApiResponse.error(message));
    }

    /** 401 Unauthorized – missing or invalid credentials. */
    public static void unauthorized(RoutingContext ctx, String message) {
        send(ctx, 401, ApiResponse.error(message));
    }

    /** 403 Forbidden – authenticated but not allowed. */
    public static void forbidden(RoutingContext ctx, String message) {
        send(ctx, 403, ApiResponse.error(message));
    }

    /** 404 Not Found – requested resource does not exist. */
    public static void notFound(RoutingContext ctx, String message) {
        send(ctx, 404, ApiResponse.error(message));
    }

    /** 409 Conflict – e.g. duplicate resource. */
    public static void conflict(RoutingContext ctx, String message) {
        send(ctx, 409, ApiResponse.error(message));
    }

    /** 422 Unprocessable Entity – validation failed. */
    public static void unprocessable(RoutingContext ctx, String message) {
        send(ctx, 422, ApiResponse.error(message));
    }

    // ── 5xx ─────────────────────────────────────────────────────────────────

    /** 500 Internal Server Error – generic server-side failure. */
    public static void internalError(RoutingContext ctx, String message) {
        send(ctx, 500, ApiResponse.error(message));
    }

    /**
     * 500 Internal Server Error – logs the throwable and returns a safe message
     * to the client (the raw exception is never exposed).
     */
    public static void internalError(RoutingContext ctx, Throwable cause) {
        ctx.vertx()   // use Vert.x logger rather than a hard dependency
                .executeBlocking(() -> {
                    System.err.println("[ResponseUtil] Unhandled exception: " + cause.getMessage());
                    cause.printStackTrace();
                    return null;
                });
        send(ctx, 500, ApiResponse.error("An unexpected error occurred"));
    }

    // ── Generic / escape hatch ───────────────────────────────────────────────

    /**
     * Send any HTTP status code with an {@link ApiResponse} body.
     * Prefer the named helpers above for readability.
     */
    public static <T> void send(RoutingContext ctx, int statusCode, ApiResponse<T> body) {
        HttpServerResponse response = ctx.response();
        if (response.ended()) {
            return; // guard against accidental double-end
        }
        response
                .setStatusCode(statusCode)
                .putHeader("Content-Type", CONTENT_TYPE)
                .end(Json.encodePrettily(body));
    }
}

