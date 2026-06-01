package com.placements.service;

import com.placements.dto.request.ApplyForJobRequest;
import com.placements.model.Application;
import com.placements.model.ApplicationAnswer;
import com.placements.repository.ApplicationRepository;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class ApplicationService {

    // Allowed status transitions — enforce a simple state machine
    private static final List<String> VALID_STATUSES = List.of(
            "Applied", "Shortlisted", "Interview Scheduled",
            "Interview Completed", "Selected", "Rejected", "On Hold"
    );

    private final ApplicationRepository repository;

    public ApplicationService(ApplicationRepository repository) {
        this.repository = repository;
    }

    // ── Apply for a job ────────────────────────────────────────────────────────

    public Future<JsonObject> applyForJob(ApplyForJobRequest req) {
        // Prevent duplicate application for the same student + job
        return repository.findByFilter(new JsonObject()
                        .put("studentId", req.getStudentId())
                        .put("jobId", req.getJobId()))
                .compose(existing -> {
                    if (!existing.isEmpty()) {
                        return Future.failedFuture(
                                "Student " + req.getStudentId()
                                        + " has already applied for job " + req.getJobId());
                    }

                    Application app = mapRequestToModel(req);
                    return repository.insert(app.toInsertDoc());
                })
                .compose(generatedId ->
                        repository.findByApplicationId(req.getApplicationId()));
    }

    // ── Read ───────────────────────────────────────────────────────────────────

    public Future<JsonArray> getAllApplications(JsonObject filters) {
        JsonObject query = buildFilterQuery(filters);
        return repository.findAll(query)
                .map(this::listToJsonArray);
    }

    public Future<JsonObject> getApplicationById(String applicationId) {
        return repository.findByApplicationId(applicationId)
                .compose(doc -> {
                    if (doc == null) {
                        return Future.failedFuture("Application not found: " + applicationId);
                    }
                    return Future.succeededFuture(doc);
                });
    }

    // ── Status update ──────────────────────────────────────────────────────────

    public Future<JsonObject> updateStatus(String applicationId, String newStatus) {
        if (!VALID_STATUSES.contains(newStatus)) {
            return Future.failedFuture(
                    "Invalid status '" + newStatus + "'. Allowed: " + VALID_STATUSES);
        }

        return repository.findByApplicationId(applicationId)
                .compose(existing -> {
                    if (existing == null) {
                        return Future.failedFuture("Application not found: " + applicationId);
                    }
                    return repository.updateStatus(applicationId, newStatus);
                })
                .compose(ignored -> repository.findByApplicationId(applicationId));
    }

    // ── Export ─────────────────────────────────────────────────────────────────

    /**
     * Returns all applications for a placement, optionally narrowed by jobId and/or status.
     * The handler converts this JsonArray to CSV.
     */
    public Future<JsonArray> exportApplications(String placementId, String jobId, String status) {
        Future<List<JsonObject>> fetched;

        if (jobId != null && !jobId.isBlank()) {
            fetched = repository.findByPlacementAndJob(placementId, jobId);
        } else {
            fetched = repository.findByPlacementId(placementId);
        }

        return fetched.map(list -> {
            List<JsonObject> filtered = list;
            if (status != null && !status.isBlank()) {
                filtered = list.stream()
                        .filter(doc -> status.equalsIgnoreCase(doc.getString("status")))
                        .collect(Collectors.toList());
            }
            return listToJsonArray(filtered);
        });
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private Application mapRequestToModel(ApplyForJobRequest req) {
        Application app = new Application();
        app.setApplicationId(req.getApplicationId());
        app.setStudentId(req.getStudentId());
        app.setRollNo(req.getRollNo());
        app.setStudentName(req.getStudentName());
        app.setPlacementId(req.getPlacementId());
        app.setJobId(req.getJobId());
        app.setCompanyId(req.getCompanyId());
        app.setCompanyName(req.getCompanyName());
        app.setAppliedDate(req.getAppliedDate());
        app.setStatus(req.getStatus());
        app.setResumeUrl(req.getResumeUrl());

        if (req.getFormAnswers() != null) {
            app.setFormAnswers(req.getFormAnswers().stream()
                    .map(entry -> {
                        ApplicationAnswer ans = new ApplicationAnswer();
                        ans.setAnswerId(entry.getAnswerId());
                        ans.setFieldId(entry.getFieldId());
                        ans.setAnswer(entry.getAnswer());
                        return ans;
                    }).collect(Collectors.toList()));
        }
        return app;
    }

    /**
     * Build a MongoDB query from optional query-param filters.
     * Supported keys: placementId, jobId, companyId, studentId, status.
     */
    private JsonObject buildFilterQuery(JsonObject filters) {
        JsonObject query = new JsonObject();
        if (filters == null) return query;

        List.of("placementId", "jobId", "companyId", "studentId", "status")
                .forEach(key -> {
                    String val = filters.getString(key);
                    if (val != null && !val.isBlank()) query.put(key, val);
                });
        return query;
    }

    private JsonArray listToJsonArray(List<JsonObject> list) {
        JsonArray arr = new JsonArray();
        list.forEach(arr::add);
        return arr;
    }
}

