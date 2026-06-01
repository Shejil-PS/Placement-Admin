package com.placements.router;

import com.placements.handler.*;
import com.placements.repository.BatchRepository;
import com.placements.service.BatchService;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class BatchRouter {

    private final Router router;

    public BatchRouter(Router router, MongoClient mongoClient) {
        this.router = router;

        BatchRepository repository = new BatchRepository(mongoClient);
        BatchService service = new BatchService(repository);

        GetBatchesHandler getBatchesHandler = new GetBatchesHandler(service);
        GetBatchByIdHandler getBatchByIdHandler = new GetBatchByIdHandler(service);
        CreateBatchHandler createBatchHandler = new CreateBatchHandler(service);
        UpdateBatchHandler updateBatchHandler = new UpdateBatchHandler(service);
        DeleteBatchHandler deleteBatchHandler = new DeleteBatchHandler(service);

        router.route("/api/batches*").handler(BodyHandler.create());

        router.get("/api/batches").handler(getBatchesHandler::handle);
        router.get("/api/batches/:id").handler(getBatchByIdHandler::handle);
        router.post("/api/batches").handler(createBatchHandler::handle);
        router.put("/api/batches/:id").handler(updateBatchHandler::handle);
        router.delete("/api/batches/:id").handler(deleteBatchHandler::handle);
    }

    public Router getRouter() {
        return router;
    }
}

