package com.placements.dto.request;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CreatePlacementRequest {

    private String id;
    private String placementCode;
    private String companyId;
    private String companyName;
    private String batchCode;
    private String driveStart;
    private String driveEnd;
    private List<AddPlacementJobRequest> jobs;

    public CreatePlacementRequest() {
        this.jobs = new ArrayList<>();
    }

    public static CreatePlacementRequest fromJson(JsonObject json) {
        CreatePlacementRequest req = new CreatePlacementRequest();
        req.setId(json.getString("_id"));
        req.setPlacementCode(json.getString("placementCode"));
        req.setCompanyId(json.getString("companyId"));
        req.setCompanyName(json.getString("companyName"));
        req.setBatchCode(json.getString("batchCode"));
        req.setDriveStart(json.getString("driveStart"));
        req.setDriveEnd(json.getString("driveEnd"));

        JsonArray jobsArray = json.getJsonArray("jobs", new JsonArray());
        List<AddPlacementJobRequest> jobs = new ArrayList<>();
        for (int i = 0; i < jobsArray.size(); i++) {
            jobs.add(AddPlacementJobRequest.fromJson(jobsArray.getJsonObject(i)));
        }
        req.setJobs(jobs);
        return req;
    }

    public String validate() {
        if (placementCode == null || placementCode.isBlank()) return "placementCode is required";
        if (companyId == null || companyId.isBlank()) return "companyId is required";
        if (companyName == null || companyName.isBlank()) return "companyName is required";
        if (batchCode == null || batchCode.isBlank()) return "batchCode is required";
        if (driveStart == null || driveStart.isBlank()) return "driveStart is required";
        if (driveEnd == null || driveEnd.isBlank()) return "driveEnd is required";
        return null;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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

    public List<AddPlacementJobRequest> getJobs() { return jobs; }
    public void setJobs(List<AddPlacementJobRequest> jobs) { this.jobs = jobs; }
}
