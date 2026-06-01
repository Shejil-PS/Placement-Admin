package com.placements.model;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Placement {

    private String id;
    private String placementCode;
    private String companyId;
    private String companyName;
    private String batchCode;
    private String driveStart;
    private String driveEnd;
    private List<PlacementJob> jobs;

    public Placement() {
        this.jobs = new ArrayList<>();
    }

    public static Placement fromJson(JsonObject json) {
        Placement p = new Placement();
        p.setId(json.getString("_id"));
        p.setPlacementCode(json.getString("placementCode"));
        p.setCompanyId(json.getString("companyId"));
        p.setCompanyName(json.getString("companyName"));
        p.setBatchCode(json.getString("batchCode"));
        p.setDriveStart(json.getString("driveStart"));
        p.setDriveEnd(json.getString("driveEnd"));

        JsonArray jobsArray = json.getJsonArray("jobs", new JsonArray());
        List<PlacementJob> jobs = new ArrayList<>();
        for (int i = 0; i < jobsArray.size(); i++) {
            jobs.add(PlacementJob.fromJson(jobsArray.getJsonObject(i)));
        }
        p.setJobs(jobs);
        return p;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (id != null) json.put("_id", id);
        json.put("placementCode", placementCode);
        json.put("companyId", companyId);
        json.put("companyName", companyName);
        json.put("batchCode", batchCode);
        json.put("driveStart", driveStart);
        json.put("driveEnd", driveEnd);

        JsonArray jobsArray = new JsonArray();
        if (jobs != null) {
            for (PlacementJob job : jobs) {
                jobsArray.add(job.toJson());
            }
        }
        json.put("jobs", jobsArray);
        return json;
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

    public List<PlacementJob> getJobs() { return jobs; }
    public void setJobs(List<PlacementJob> jobs) { this.jobs = jobs; }
}
