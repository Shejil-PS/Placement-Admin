package com.placements.router;

import com.placements.handler.*;
import com.placements.repository.StudentRepository;
import com.placements.service.StudentService;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class StudentRouter {

    private final Router router;

    public StudentRouter(Router router, MongoClient mongoClient) {
        this.router = router;

        StudentRepository repository = new StudentRepository(mongoClient);
        StudentService service = new StudentService(repository);

        GetStudentsHandler getStudentsHandler = new GetStudentsHandler(service);
        GetStudentByIdHandler getStudentByIdHandler = new GetStudentByIdHandler(service);
        CreateStudentHandler createStudentHandler = new CreateStudentHandler(service);
        UpdateStudentHandler updateStudentHandler = new UpdateStudentHandler(service);
        DeleteStudentHandler deleteStudentHandler = new DeleteStudentHandler(service);

        router.route("/api/students*").handler(BodyHandler.create());

        router.get("/api/students").handler(getStudentsHandler::handle);
        router.get("/api/students/:id").handler(getStudentByIdHandler::handle);
        router.post("/api/students").handler(createStudentHandler::handle);
        router.put("/api/students/:id").handler(updateStudentHandler::handle);
        router.delete("/api/students/:id").handler(deleteStudentHandler::handle);
    }

    public Router getRouter() {
        return router;
    }
}

