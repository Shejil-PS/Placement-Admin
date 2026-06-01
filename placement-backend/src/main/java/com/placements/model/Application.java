package com.placements.model;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Application {

    private String oid;           // Mongo ObjectId hex string → "_id.$oid"
    private String applicationId;
    private String studentId;
    private String rollNo;
    private String studentName;
    private String placementId;
    private String jobId;
    private String companyId;
    private String companyName;
    private String appliedDate;
    private String status;
    private String resumeUrl;
    private List<ApplicationAnswer> formAnswers;

    public Application() {
        this.formAnswers = new ArrayList<>();
    }

    /**
     * Deserialize from a MongoDB document returned by Vert.x MongoClient.
     * Vert.x MongoClient returns "_id" as a JsonObject {"$oid": "..."} for
     * ObjectId fields.
     */
    public static Application fromJson(JsonObject json) {
        Application app = new Application();

        // Handle ObjectId: {"$oid": "..."}
        Object rawId = json.getValue("_id");
        if (rawId instanceof JsonObject) {
            app.setOid(((JsonObject) rawId).getString("$oid"));
        } else if (rawId instanceof String) {
            app.setOid((String) rawId);
        }

        app.setApplicationId(json.getString("applicationId"));
        app.setStudentId(json.getString("studentId"));
        app.setRollNo(json.getString("rollNo"));
        app.setStudentName(json.getString("studentName"));
        app.setPlacementId(json.getString("placementId"));
        app.setJobId(json.getString("jobId"));
        app.setCompanyId(json.getString("companyId"));
        app.setCompanyName(json.getString("companyName"));
        app.setAppliedDate(json.getString("appliedDate"));
        app.setStatus(json.getString("status"));
        app.setResumeUrl(json.getString("resumeUrl"));

        JsonArray answersArray = json.getJsonArray("formAnswers", new JsonArray());
        List<ApplicationAnswer> answers = new ArrayList<>();
        for (int i = 0; i < answersArray.size(); i++) {
            answers.add(ApplicationAnswer.fromJson(answersArray.getJsonObject(i)));
        }
        app.setFormAnswers(answers);

        return app;
    }

    /**
     * Serialize to a MongoDB-compatible document.
     * _id is omitted on insert so MongoDB generates the ObjectId automatically.
     */
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        if (oid != null && !oid.isBlank()) {
            json.put("_id", new JsonObject().put("$oid", oid));
        }

        json.put("applicationId", applicationId);
        json.put("studentId", studentId);
        json.put("rollNo", rollNo);
        json.put("studentName", studentName);
        json.put("placementId", placementId);
        json.put("jobId", jobId);
        json.put("companyId", companyId);
        json.put("companyName", companyName);
        json.put("appliedDate", appliedDate);
        json.put("status", status);
        json.put("resumeUrl", resumeUrl);

        JsonArray answersArray = new JsonArray();
        if (formAnswers != null) {
            formAnswers.forEach(a -> answersArray.add(a.toJson()));
        }
        json.put("formAnswers", answersArray);

        return json;
    }

    /**
     * Returns a document suitable for insert (no _id — MongoDB will generate one).
     */
    public JsonObject toInsertDoc() {
        JsonObject doc = toJson();
        doc.remove("_id");
        return doc;
    }

    // Getters and Setters
    public String getOid() { return oid; }
    public void setOid(String oid) { this.oid = oid; }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getPlacementId() { return placementId; }
    public void setPlacementId(String placementId) { this.placementId = placementId; }

    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getAppliedDate() { return appliedDate; }
    public void setAppliedDate(String appliedDate) { this.appliedDate = appliedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public List<ApplicationAnswer> getFormAnswers() { return formAnswers; }
    public void setFormAnswers(List<ApplicationAnswer> formAnswers) { this.formAnswers = formAnswers; }
}
