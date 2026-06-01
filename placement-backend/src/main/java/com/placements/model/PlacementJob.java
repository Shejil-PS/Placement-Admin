package com.placements.model;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class PlacementJob {

    private String jobId;
    private String companyId;
    private String role;
    private String description;
    private String eligibleBatches;
    private String employmentType;
    private double packageLPA;
    private double minCGPA;
    private boolean active;
    private boolean allowBacklog;
    private List<PlacementJobField> fields;

    public PlacementJob() {
        this.fields = new ArrayList<>();
    }

    public static PlacementJob fromJson(JsonObject json) {
        PlacementJob job = new PlacementJob();
        job.setJobId(json.getString("jobId"));
        job.setCompanyId(json.getString("companyId"));
        job.setRole(json.getString("role"));

        // Description may be NaN stored as $numberDouble; treat as nullable String
        Object desc = json.getValue("Description");
        if (desc instanceof JsonObject) {
            job.setDescription(null); // was NaN
        } else if (desc instanceof String) {
            job.setDescription((String) desc);
        } else {
            job.setDescription(null);
        }

        job.setEligibleBatches(json.getString("eligibleBatches"));
        job.setEmploymentType(json.getString("employmentType"));
        job.setPackageLPA(json.getDouble("packageLPA", 0.0));
        job.setMinCGPA(json.getDouble("minCGPA", 0.0));
        job.setActive(json.getBoolean("active", true));
        job.setAllowBacklog(json.getBoolean("allowBacklog", false));

        JsonArray fieldsArray = json.getJsonArray("fields", new JsonArray());
        List<PlacementJobField> fields = new ArrayList<>();
        for (int i = 0; i < fieldsArray.size(); i++) {
            fields.add(PlacementJobField.fromJson(fieldsArray.getJsonObject(i)));
        }
        job.setFields(fields);
        return job;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.put("jobId", jobId);
        json.put("companyId", companyId);
        json.put("role", role);
        json.put("Description", description);
        json.put("eligibleBatches", eligibleBatches);
        json.put("employmentType", employmentType);
        json.put("packageLPA", packageLPA);
        json.put("minCGPA", minCGPA);
        json.put("active", active);
        json.put("allowBacklog", allowBacklog);

        JsonArray fieldsArray = new JsonArray();
        if (fields != null) {
            for (PlacementJobField field : fields) {
                fieldsArray.add(field.toJson());
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

    public boolean isAllowBacklog() { return allowBacklog; }
    public void setAllowBacklog(boolean allowBacklog) { this.allowBacklog = allowBacklog; }

    public List<PlacementJobField> getFields() { return fields; }
    public void setFields(List<PlacementJobField> fields) { this.fields = fields; }
}