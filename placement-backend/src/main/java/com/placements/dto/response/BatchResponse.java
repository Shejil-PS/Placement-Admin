package com.placements.dto.response;

import com.placements.model.Batch;
import io.vertx.core.json.JsonObject;

public class BatchResponse {

    private String oid;
    private String batchId;
    private String batchCode;
    private String batchName;
    private String department;

    public BatchResponse() {}

    public static BatchResponse fromBatch(Batch batch) {
        BatchResponse res = new BatchResponse();
        res.setOid(batch.getOid());
        res.setBatchId(batch.getBatchId());
        res.setBatchCode(batch.getBatchCode());
        res.setBatchName(batch.getBatchName());
        res.setDepartment(batch.getDepartment());
        return res;
    }

    public static BatchResponse fromJson(JsonObject json) {
        return fromBatch(Batch.fromJson(json));
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

