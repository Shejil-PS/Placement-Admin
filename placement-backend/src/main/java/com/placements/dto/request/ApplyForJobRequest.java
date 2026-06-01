package com.placements.dto.request;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class ApplyForJobRequest {

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
    private List<AnswerEntry> formAnswers;

    public ApplyForJobRequest() {
        this.formAnswers = new ArrayList<>();
        this.status = "Applied"; // sensible default on creation
    }

    public static ApplyForJobRequest fromJson(JsonObject json) {
        ApplyForJobRequest req = new ApplyForJobRequest();
        req.setApplicationId(json.getString("applicationId"));
        req.setStudentId(json.getString("studentId"));
        req.setRollNo(json.getString("rollNo"));
        req.setStudentName(json.getString("studentName"));
        req.setPlacementId(json.getString("placementId"));
        req.setJobId(json.getString("jobId"));
        req.setCompanyId(json.getString("companyId"));
        req.setCompanyName(json.getString("companyName"));
        req.setAppliedDate(json.getString("appliedDate"));

        // Allow caller to override default status
        String status = json.getString("status");
        if (status != null && !status.isBlank()) req.setStatus(status);

        req.setResumeUrl(json.getString("resumeUrl"));

        JsonArray answersArray = json.getJsonArray("formAnswers", new JsonArray());
        List<AnswerEntry> answers = new ArrayList<>();
        for (int i = 0; i < answersArray.size(); i++) {
            answers.add(AnswerEntry.fromJson(answersArray.getJsonObject(i)));
        }
        req.setFormAnswers(answers);

        return req;
    }

    /**
     * Returns null when valid, or an error message string.
     */
    public String validate() {
        if (applicationId == null || applicationId.isBlank()) return "applicationId is required";
        if (studentId == null || studentId.isBlank())         return "studentId is required";
        if (rollNo == null || rollNo.isBlank())               return "rollNo is required";
        if (studentName == null || studentName.isBlank())     return "studentName is required";
        if (placementId == null || placementId.isBlank())     return "placementId is required";
        if (jobId == null || jobId.isBlank())                 return "jobId is required";
        if (companyId == null || companyId.isBlank())         return "companyId is required";
        if (companyName == null || companyName.isBlank())     return "companyName is required";
        if (appliedDate == null || appliedDate.isBlank())     return "appliedDate is required";
        return null;
    }

    // ── Nested answer DTO ──────────────────────────────────────────────────────

    public static class AnswerEntry {
        private String answerId;
        private String fieldId;
        private String answer;

        public static AnswerEntry fromJson(JsonObject json) {
            AnswerEntry e = new AnswerEntry();
            e.setAnswerId(json.getString("answerId"));
            e.setFieldId(json.getString("fieldId"));
            e.setAnswer(json.getString("answer"));
            return e;
        }

        public JsonObject toJson() {
            return new JsonObject()
                    .put("answerId", answerId)
                    .put("fieldId", fieldId)
                    .put("answer", answer);
        }

        public String getAnswerId() { return answerId; }
        public void setAnswerId(String answerId) { this.answerId = answerId; }

        public String getFieldId() { return fieldId; }
        public void setFieldId(String fieldId) { this.fieldId = fieldId; }

        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }
    }

    // Getters and Setters
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

    public List<AnswerEntry> getFormAnswers() { return formAnswers; }
    public void setFormAnswers(List<AnswerEntry> formAnswers) { this.formAnswers = formAnswers; }
}
