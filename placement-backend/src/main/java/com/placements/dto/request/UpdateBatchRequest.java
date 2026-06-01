package com.placements.dto.request;

import io.vertx.core.json.JsonObject;

public class UpdateBatchRequest {

    private String batchCode;
    private String batchName;
    private String department;

    public UpdateBatchRequest() {}

    public static UpdateBatchRequest fromJson(JsonObject json) {
        UpdateBatchRequest req = new UpdateBatchRequest();
        if (json.containsKey("batchCode")) req.setBatchCode(json.getString("batchCode"));
        if (json.containsKey("batchName")) req.setBatchName(json.getString("batchName"));
        if (json.containsKey("department")) req.setDepartment(json.getString("department"));
        return req;
    }

    public JsonObject toUpdateDocument() {
        JsonObject set = new JsonObject();
        if (batchCode != null) set.put("batchCode", batchCode);
        if (batchName != null) set.put("batchName", batchName);
        if (department != null) set.put("department", department);
        return new JsonObject().put("$set", set);
    }

    public boolean isEmpty() {
        return batchCode == null && batchName == null && department == null;
    }

    // Getters and Setters
    public String getBatchCode() { return batchCode; }
    public void setBatchCode(String batchCode) { this.batchCode = batchCode; }

    public String getBatchName() { return batchName; }
    public void setBatchName(String batchName) { this.batchName = batchName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
