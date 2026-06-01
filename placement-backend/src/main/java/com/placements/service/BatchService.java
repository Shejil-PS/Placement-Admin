package com.placements.service;

import com.placements.dto.request.CreateBatchRequest;
import com.placements.dto.request.UpdateBatchRequest;
import com.placements.dto.response.BatchResponse;
import com.placements.model.Batch;
import com.placements.repository.BatchRepository;
import io.vertx.core.Future;

import java.util.List;
import java.util.stream.Collectors;

public class BatchService {

    private final BatchRepository batchRepository;

    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    public Future<List<BatchResponse>> getAllBatches() {
        return batchRepository.findAll()
                .map(batches -> batches.stream()
                        .map(BatchResponse::fromBatch)
                        .collect(Collectors.toList()));
    }

    public Future<BatchResponse> getBatchById(String oid) {
        return batchRepository.findById(oid)
                .map(batch -> {
                    if (batch == null) {
                        throw new RuntimeException("Batch not found with id: " + oid);
                    }
                    return BatchResponse.fromBatch(batch);
                });
    }

    public Future<BatchResponse> createBatch(CreateBatchRequest request) {
        Batch batch = new Batch();
        batch.setBatchId(request.getBatchId());
        batch.setBatchCode(request.getBatchCode());
        batch.setBatchName(request.getBatchName());
        batch.setDepartment(request.getDepartment());

        return batchRepository.create(batch)
                .map(BatchResponse::fromBatch);
    }

    public Future<BatchResponse> updateBatch(String oid, UpdateBatchRequest request) {
        if (request.isEmpty()) {
            return Future.failedFuture("No fields provided for update");
        }
        return batchRepository.update(oid, request.toUpdateDocument())
                .map(updated -> {
                    if (updated == null) {
                        throw new RuntimeException("Batch not found with id: " + oid);
                    }
                    return BatchResponse.fromBatch(updated);
                });
    }

    public Future<Void> deleteBatch(String oid) {
        return batchRepository.delete(oid)
                .compose(deleted -> {
                    if (!deleted) {
                        return Future.failedFuture("Batch not found with id: " + oid);
                    }
                    return Future.succeededFuture();
                });
    }
}

