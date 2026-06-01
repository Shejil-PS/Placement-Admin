package com.placements.handler;

import com.placements.dto.request.CreateStudentRequest;
import com.placements.service.StudentService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class CreateStudentHandler {

    private final StudentService studentService;

    public CreateStudentHandler(StudentService studentService) {
        this.studentService = studentService;
    }

    public void handle(RoutingContext ctx) {
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

        CreateStudentRequest request = CreateStudentRequest.fromJson(body);
        String validationError = request.validate();
        if (validationError != null) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("Content-Type", "application/json")
                    .end(errorBody(validationError));
            return;
        }

        studentService.createStudent(request)
                .onSuccess(student -> ctx.response()
                        .setStatusCode(201)
                        .putHeader("Content-Type", "application/json")
                        .end(student.toJson().encode()))
                .onFailure(err -> ctx.response()
                        .setStatusCode(500)
                        .putHeader("Content-Type", "application/json")
                        .end(errorBody(err.getMessage())));
    }

    private String errorBody(String message) {
        return new JsonObject().put("error", message).encode();
    }
}

