package com.placements.handler;

import com.placements.service.StudentService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class GetStudentByIdHandler {

    private final StudentService studentService;

    public GetStudentByIdHandler(StudentService studentService) {
        this.studentService = studentService;
    }

    public void handle(RoutingContext ctx) {
        String id = ctx.pathParam("id");

        studentService.getStudentById(id)
                .onSuccess(student -> ctx.response()
                        .setStatusCode(200)
                        .putHeader("Content-Type", "application/json")
                        .end(student.toJson().encode()))
                .onFailure(err -> {
                    String msg = err.getMessage();
                    int status = (msg != null && msg.contains("not found")) ? 404 : 500;
                    ctx.response()
                            .setStatusCode(status)
                            .putHeader("Content-Type", "application/json")
                            .end(errorBody(msg));
                });
    }

    private String errorBody(String message) {
        return new JsonObject().put("error", message).encode();
    }
}

