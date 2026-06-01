package com.placements.dto.request;

import io.vertx.core.json.JsonObject;

public class UpdatePlacementRequest {

    private String placementCode;
    private String companyId;
    private String companyName;
    private String batchCode;
    private String driveStart;
    private String driveEnd;

    public UpdatePlacementRequest() {}

    public static UpdatePlacementRequest fromJson(JsonObject json) {
        UpdatePlacementRequest req = new UpdatePlacementRequest();
        req.setPlacementCode(json.getString("placementCode"));
        req.setCompanyId(json.getString("companyId"));
        req.setCompanyName(json.getString("companyName"));
        req.setBatchCode(json.getString("batchCode"));
        req.setDriveStart(json.getString("driveStart"));
        req.setDriveEnd(json.getString("driveEnd"));
        return req;
    }

    public JsonObject toUpdateDoc() {
        JsonObject set = new JsonObject();
        if (placementCode != null) set.put("placementCode", placementCode);
        if (companyId != null) set.put("companyId", companyId);
        if (companyName != null) set.put("companyName", companyName);
        if (batchCode != null) set.put("batchCode", batchCode);
        if (driveStart != null) set.put("driveStart", driveStart);
        if (driveEnd != null) set.put("driveEnd", driveEnd);
        return new JsonObject().put("$set", set);
    }

    // Getters and Setters
    public String getPlacementCode() { return placementCode; }
    public void setPlacementCode(String placementCode) { this.placementCode = placementCode; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getBatchCode() { return batchCode; }
    public void setBatchCode(String batchCode) { this.batchCode = batchCode; }

    public String getDriveStart() { return driveStart; }
    public void setDriveStart(String driveStart) { this.driveStart = driveStart; }

    public String getDriveEnd() { return driveEnd; }
    public void setDriveEnd(String driveEnd) { this.driveEnd = driveEnd; }
}
