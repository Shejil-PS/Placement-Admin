package com.placements.model;

import io.vertx.core.json.JsonObject;

public class ApplicationAnswer {

    private String answerId;
    private String fieldId;
    private String answer;

    public ApplicationAnswer() {}

    public static ApplicationAnswer fromJson(JsonObject json) {
        ApplicationAnswer a = new ApplicationAnswer();
        a.setAnswerId(json.getString("answerId"));
        a.setFieldId(json.getString("fieldId"));
        a.setAnswer(json.getString("answer"));
        return a;
    }

    public JsonObject toJson() {
        return new JsonObject()
                .put("answerId", answerId)
                .put("fieldId", fieldId)
                .put("answer", answer);
    }

    // Getters and Setters
    public String getAnswerId() { return answerId; }
    public void setAnswerId(String answerId) { this.answerId = answerId; }

    public String getFieldId() { return fieldId; }
    public void setFieldId(String fieldId) { this.fieldId = fieldId; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}
