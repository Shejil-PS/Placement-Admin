package com.placements.dto.request;

import io.vertx.core.json.JsonObject;

public class CreateBatchRequest {

    private String batchId;    // maps to "_id " (trailing space)
    private String batchCode;
    private String batchName;
    private String department;

    public CreateBatchRequest() {}

    public static CreateBatchRequest fromJson(JsonObject json) {
        CreateBatchRequest req = new CreateBatchRequest();
        // Accept both "batchId" from clients and the raw "_id " field
        String id = json.getString("batchId");
        if (id == null) id = json.getString("_id ");
        req.setBatchId(id);
        req.setBatchCode(json.getString("batchCode"));
        req.setBatchName(json.getString("batchName"));
        req.setDepartment(json.getString("department"));
        return req;
    }

    public String validate() {
        if (batchId == null || batchId.isBlank()) return "batchId is required";
        if (batchCode == null || batchCode.isBlank()) return "batchCode is required";
        if (batchName == null || batchName.isBlank()) return "batchName is required";
        if (department == null || department.isBlank()) return "department is required";
        return null;
    }

    // Getters and Setters
    public String getBatchId() { return batchId; }
    public void setBatchId(String batchId) { this.batchId = batchId; }

    public String getBatchCode() { return batchCode; }
    public void setBatchCode(String batchCode) { this.batchCode = batchCode; }

    public String getBatchName() { return batchName; }
    public void setBatchName(String batchName) { this.batchName = batchName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
