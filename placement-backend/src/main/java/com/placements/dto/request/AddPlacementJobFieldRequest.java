package com.placements.dto.request;

import io.vertx.core.json.JsonObject;

public class AddPlacementJobFieldRequest {

    private String fieldId;
    private String label;
    private String fieldType;
    private boolean required;

    public AddPlacementJobFieldRequest() {}

    public static AddPlacementJobFieldRequest fromJson(JsonObject json) {
        AddPlacementJobFieldRequest req = new AddPlacementJobFieldRequest();
        req.setFieldId(json.getString("fieldId"));
        req.setLabel(json.getString("label"));
        req.setFieldType(json.getString("fieldType"));
        req.setRequired(json.getBoolean("required", false));
        return req;
    }

    public String validate() {
        if (fieldId == null || fieldId.isBlank()) return "fieldId is required";
        if (label == null || label.isBlank()) return "label is required";
        if (fieldType == null || fieldType.isBlank()) return "fieldType is required";
        return null;
    }

    public JsonObject toJson() {
        return new JsonObject()
                .put("fieldId", fieldId)
                .put("label", label)
                .put("fieldType", fieldType)
                .put("required", required);
    }

    // Getters and Setters
    public String getFieldId() { return fieldId; }
    public void setFieldId(String fieldId) { this.fieldId = fieldId; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getFieldType() { return fieldType; }
    public void setFieldType(String fieldType) { this.fieldType = fieldType; }

    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }
}
