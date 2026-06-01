package com.placements.model;

import io.vertx.core.json.JsonObject;

public class Batch {

    private String oid;        // MongoDB ObjectId → _id.$oid
    private String batchId;    // Business key   → "_id " (trailing space)
    private String batchCode;
    private String batchName;
    private String department;

    public Batch() {}

    public static Batch fromJson(JsonObject json) {
        Batch b = new Batch();

        Object rawId = json.getValue("_id");
        if (rawId instanceof JsonObject) {
            b.setOid(((JsonObject) rawId).getString("$oid"));
        } else if (rawId instanceof String) {
            b.setOid((String) rawId);
        }

        // Business key field name has a trailing space: "_id "
        b.setBatchId(json.getString("_id "));
        b.setBatchCode(json.getString("batchCode"));
        b.setBatchName(json.getString("batchName"));
        b.setDepartment(json.getString("department"));
        return b;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (oid != null) {
            json.put("_id", new JsonObject().put("$oid", oid));
        }
        json.put("_id ", batchId);   // trailing space preserved
        json.put("batchCode", batchCode);
        json.put("batchName", batchName);
        json.put("department", department);
        return json;
    }

    // Getters and Setters
    public String getOid() { return oid; }
    public void setOid(String oid) { this.oid = oid; }

    public String getBatchId() { return batchId; }
    public void setBatchId(String batchId) { this.batchId = batchId; }

    public String getBatchCode() { return batchCode; }
    public void setBatchCode(String batchCode) { this.batchCode = batchCode; }

    public String getBatchName() { return batchName; }
    public void setBatchName(String batchName) { this.batchName = batchName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
