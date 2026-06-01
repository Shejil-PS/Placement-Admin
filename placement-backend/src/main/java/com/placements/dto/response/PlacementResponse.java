package com.placements.dto.response;

import io.vertx.core.json.JsonObject;

public class PlacementResponse {

    private boolean success;
    private String message;
    private Object data;

    public PlacementResponse() {}

    public static PlacementResponse success(Object data) {
        PlacementResponse res = new PlacementResponse();
        res.setSuccess(true);
        res.setData(data);
        return res;
    }

    public static PlacementResponse success(String message, Object data) {
        PlacementResponse res = new PlacementResponse();
        res.setSuccess(true);
        res.setMessage(message);
        res.setData(data);
        return res;
    }

    public static PlacementResponse error(String message) {
        PlacementResponse res = new PlacementResponse();
        res.setSuccess(false);
        res.setMessage(message);
        return res;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject()
                .put("success", success);
        if (message != null) json.put("message", message);
        if (data != null) {
            if (data instanceof JsonObject) {
                json.put("data", (JsonObject) data);
            } else if (data instanceof io.vertx.core.json.JsonArray) {
                json.put("data", (io.vertx.core.json.JsonArray) data);
            } else {
                json.put("data", data.toString());
            }
        }
        return json;
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}
