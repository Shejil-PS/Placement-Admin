package com.placements.service;

import com.placements.dto.request.*;
import com.placements.model.Placement;
import com.placements.model.PlacementJob;
import com.placements.model.PlacementJobField;
import com.placements.repository.PlacementRepository;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class PlacementService {

    private final PlacementRepository repository;

    public PlacementService(PlacementRepository repository) {
        this.repository = repository;
    }

    // ── Placement CRUD ─────────────────────────────────────────────────────────

    public Future<JsonArray> getAllPlacements() {
        return repository.findAll()
                .map(list -> {
                    JsonArray arr = new JsonArray();
                    list.forEach(arr::add);
                    return arr;
                });
    }

    public Future<JsonObject> getPlacementById(String id) {
        return repository.findById(id)
                .compose(doc -> {
                    if (doc == null) {
                        return Future.failedFuture("Placement not found: " + id);
                    }
                    return Future.succeededFuture(doc);
                });
    }

    public Future<JsonObject> createPlacement(CreatePlacementRequest req) {
        Placement placement = new Placement();
        placement.setId(req.getId());
        placement.setPlacementCode(req.getPlacementCode());
        placement.setCompanyId(req.getCompanyId());
        placement.setCompanyName(req.getCompanyName());
        placement.setBatchCode(req.getBatchCode());
        placement.setDriveStart(req.getDriveStart());
        placement.setDriveEnd(req.getDriveEnd());

        if (req.getJobs() != null) {
            placement.setJobs(req.getJobs().stream()
                    .map(jobReq -> {
                        PlacementJob job = new PlacementJob();
                        job.setJobId(jobReq.getJobId());
                        job.setCompanyId(jobReq.getCompanyId());
                        job.setRole(jobReq.getRole());
                        job.setDescription(jobReq.getDescription());
                        job.setEligibleBatches(jobReq.getEligibleBatches());
                        job.setEmploymentType(jobReq.getEmploymentType());
                        job.setPackageLPA(jobReq.getPackageLPA());
                        job.setMinCGPA(jobReq.getMinCGPA());
                        job.setActive(jobReq.isActive());
                        job.setAllowBacklog(jobReq.getAllowBacklog() != null ? jobReq.getAllowBacklog() : false);
                        if (jobReq.getFields() != null) {
                            job.setFields(jobReq.getFields().stream()
                                    .map(fr -> {
                                        PlacementJobField f = new PlacementJobField();
                                        f.setFieldId(fr.getFieldId());
                                        f.setLabel(fr.getLabel());
                                        f.setFieldType(fr.getFieldType());
                                        f.setRequired(fr.isRequired());
                                        return f;
                                    }).collect(Collectors.toList()));
                        }
                        return job;
                    }).collect(Collectors.toList()));
        }

        JsonObject document = placement.toJson();
        return repository.insert(document)
                .compose(id -> repository.findById(id != null ? id : req.getId()));
    }

    public Future<JsonObject> updatePlacement(String id, UpdatePlacementRequest req) {
        return repository.findById(id)
                .compose(existing -> {
                    if (existing == null) return Future.failedFuture("Placement not found: " + id);
                    return repository.updateById(id, req.toUpdateDoc());
                })
                .compose(ignored -> repository.findById(id));
    }

    public Future<Void> deletePlacement(String id) {
        return repository.findById(id)
                .compose(existing -> {
                    if (existing == null) return Future.failedFuture("Placement not found: " + id);
                    return repository.deleteById(id);
                })
                .mapEmpty();
    }

    // ── Job operations ─────────────────────────────────────────────────────────

    public Future<JsonObject> addJob(String placementId, AddPlacementJobRequest req) {
        return repository.findById(placementId)
                .compose(existing -> {
                    if (existing == null) return Future.failedFuture("Placement not found: " + placementId);
                    return repository.addJob(placementId, req.toJson());
                })
                .compose(ignored -> repository.findById(placementId));
    }

    public Future<JsonObject> updateJob(String placementId, String jobId, AddPlacementJobRequest req) {
        return repository.findById(placementId)
                .compose(existing -> {
                    if (existing == null) return Future.failedFuture("Placement not found: " + placementId);
                    return repository.updateJob(placementId, jobId, req.toJson());
                })
                .compose(ignored -> repository.findById(placementId));
    }

    public Future<JsonObject> deleteJob(String placementId, String jobId) {
        return repository.findById(placementId)
                .compose(existing -> {
                    if (existing == null) return Future.failedFuture("Placement not found: " + placementId);
                    return repository.deleteJob(placementId, jobId);
                })
                .compose(ignored -> repository.findById(placementId));
    }

    // ── Job Field operations ───────────────────────────────────────────────────

    public Future<JsonObject> addJobField(String placementId, String jobId,
                                          AddPlacementJobFieldRequest req) {
        return repository.findById(placementId)
                .compose(existing -> {
                    if (existing == null) return Future.failedFuture("Placement not found: " + placementId);
                    return repository.addJobField(placementId, jobId, req.toJson());
                })
                .compose(ignored -> repository.findById(placementId));
    }

    public Future<JsonObject> updateJobField(String placementId, String jobId,
                                             String fieldId, AddPlacementJobFieldRequest req) {
        return repository.findById(placementId)
                .compose(existing -> {
                    if (existing == null) return Future.failedFuture("Placement not found: " + placementId);
                    return repository.updateJobField(placementId, jobId, fieldId, req.toJson());
                })
                .compose(ignored -> repository.findById(placementId));
    }

    public Future<JsonObject> deleteJobField(String placementId, String jobId, String fieldId) {
        return repository.findById(placementId)
                .compose(existing -> {
                    if (existing == null) return Future.failedFuture("Placement not found: " + placementId);
                    return repository.deleteJobField(placementId, jobId, fieldId);
                })
                .compose(ignored -> repository.findById(placementId));
    }
}