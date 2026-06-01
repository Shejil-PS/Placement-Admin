package com.placements.dto.response;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class ApplicationResponse {

    private boolean success;
    private String message;
    private Object data;          // JsonObject | JsonArray | null

    private ApplicationResponse() {}

    // ── Factory methods ────────────────────────────────────────────────────────

    public static ApplicationResponse success(Object data) {
        ApplicationResponse r = new ApplicationResponse();
        r.success = true;
        r.data = data;
        return r;
    }

    public static ApplicationResponse success(String message, Object data) {
        ApplicationResponse r = new ApplicationResponse();
        r.success = true;
        r.message = message;
        r.data = data;
        return r;
    }

    public static ApplicationResponse error(String message) {
        ApplicationResponse r = new ApplicationResponse();
        r.success = false;
        r.message = message;
        return r;
    }

    // ── Serialization ──────────────────────────────────────────────────────────

    public JsonObject toJson() {
        JsonObject json = new JsonObject().put("success", success);
        if (message != null) json.put("message", message);
        if (data instanceof JsonObject)  json.put("data", (JsonObject) data);
        else if (data instanceof JsonArray) json.put("data", (JsonArray) data);
        else if (data != null)           json.put("data", data.toString());
        return json;
    }

    public String encode() {
        return toJson().encode();
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Object getData()    { return data; }
}
