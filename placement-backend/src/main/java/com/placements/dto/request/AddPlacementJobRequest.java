package com.placements.dto.request;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class AddPlacementJobRequest {

    private String jobId;
    private String companyId;
    private String role;
    private String description;
    private String eligibleBatches;
    private String employmentType;
    private double packageLPA;
    private double minCGPA;
    private boolean active;
    private Boolean allowBacklog; // Optional during updates, so Object or Boolean is best
    private List<AddPlacementJobFieldRequest> fields;

    public AddPlacementJobRequest() {
        this.fields = new ArrayList<>();
        this.active = true;
    }

    public static AddPlacementJobRequest fromJson(JsonObject json) {
        AddPlacementJobRequest req = new AddPlacementJobRequest();
        req.setJobId(json.getString("jobId"));
        req.setCompanyId(json.getString("companyId"));
        req.setRole(json.getString("role"));
        req.setDescription(json.getString("Description"));
        req.setEligibleBatches(json.getString("eligibleBatches"));
        req.setEmploymentType(json.getString("employmentType"));
        req.setPackageLPA(json.getDouble("packageLPA", 0.0));
        req.setMinCGPA(json.getDouble("minCGPA", 0.0));
        req.setActive(json.getBoolean("active", true));
        if (json.containsKey("allowBacklog")) {
            req.setAllowBacklog(json.getBoolean("allowBacklog"));
        }

        JsonArray fieldsArray = json.getJsonArray("fields", new JsonArray());
        List<AddPlacementJobFieldRequest> fields = new ArrayList<>();
        for (int i = 0; i < fieldsArray.size(); i++) {
            fields.add(AddPlacementJobFieldRequest.fromJson(fieldsArray.getJsonObject(i)));
        }
        req.setFields(fields);
        return req;
    }

    public String validate() {
        if (jobId == null || jobId.isBlank()) return "jobId is required";
        if (companyId == null || companyId.isBlank()) return "companyId is required";
        if (role == null || role.isBlank()) return "role is required";
        if (employmentType == null || employmentType.isBlank()) return "employmentType is required";
        return null;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject()
                .put("jobId", jobId)
                .put("companyId", companyId)
                .put("role", role)
                .put("Description", description)
                .put("eligibleBatches", eligibleBatches)
                .put("employmentType", employmentType)
                .put("packageLPA", packageLPA)
                .put("minCGPA", minCGPA)
                .put("active", active);

        if (allowBacklog != null) {
            json.put("allowBacklog", allowBacklog);
        }

        JsonArray fieldsArray = new JsonArray();
        if (fields != null) {
            for (AddPlacementJobFieldRequest f : fields) {
                fieldsArray.add(f.toJson());
            }
        }
        json.put("fields", fieldsArray);
        return json;
    }

    // Getters and Setters
    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEligibleBatches() { return eligibleBatches; }
    public void setEligibleBatches(String eligibleBatches) { this.eligibleBatches = eligibleBatches; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public double getPackageLPA() { return packageLPA; }
    public void setPackageLPA(double packageLPA) { this.packageLPA = packageLPA; }

    public double getMinCGPA() { return minCGPA; }
    public void setMinCGPA(double minCGPA) { this.minCGPA = minCGPA; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Boolean getAllowBacklog() { return allowBacklog; }
    public void setAllowBacklog(Boolean allowBacklog) { this.allowBacklog = allowBacklog; }

    public List<AddPlacementJobFieldRequest> getFields() { return fields; }
    public void setFields(List<AddPlacementJobFieldRequest> fields) { this.fields = fields; }
}