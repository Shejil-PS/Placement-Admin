package com.placements.handler;

import com.placements.service.StudentService;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;

public class GetStudentsHandler {

    private final StudentService studentService;

    public GetStudentsHandler(StudentService studentService) {
        this.studentService = studentService;
    }

    public void handle(RoutingContext ctx) {
        studentService.getAllStudents()
                .onSuccess(students -> {
                    JsonArray array = new JsonArray();
                    students.forEach(s -> array.add(s.toJson()));
                    ctx.response()
                            .setStatusCode(200)
                            .putHeader("Content-Type", "application/json")
                            .end(array.encode());
                })
                .onFailure(err -> ctx.response()
                        .setStatusCode(500)
                        .putHeader("Content-Type", "application/json")
                        .end(errorBody(err.getMessage())));
    }

    private String errorBody(String message) {
        return new io.vertx.core.json.JsonObject()
                .put("error", message)
                .encode();
    }
}

