package com.placements.handler;

import com.placements.dto.request.UpdateStudentRequest;
import com.placements.service.StudentService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UpdateStudentHandler {

    private final StudentService studentService;

    public UpdateStudentHandler(StudentService studentService) {
        this.studentService = studentService;
    }

    public void handle(RoutingContext ctx) {
        String id = ctx.pathParam("id");

        JsonObject body;
        try {
            body = ctx.body().asJsonObject();
        } catch (Exception e) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(errorBody("Invalid JSON body"));
            return;
        }

        if (body == null) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(errorBody("Request body is required"));
            return;
        }

        UpdateStudentRequest request = UpdateStudentRequest.fromJson(body);

        studentService.updateStudent(id, request)
                .onSuccess(student -> ctx.response()
                        .setStatusCode(200)
                        .putHeader("Content-Type", "application/json")
                        .end(student.toJson().encode()))
                .onFailure(err -> {
                    String msg = err.getMessage();
                    int status = (msg != null && msg.contains("not found")) ? 404
                            : (msg != null && msg.contains("No fields")) ? 400 : 500;
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

