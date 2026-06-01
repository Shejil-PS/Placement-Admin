package com.placements.model;

import io.vertx.core.json.JsonObject;

public class PlacementJobField {

    private String fieldId;
    private String label;
    private String fieldType;
    private boolean required;

    public PlacementJobField() {}

    public static PlacementJobField fromJson(JsonObject json) {
        PlacementJobField field = new PlacementJobField();
        field.setFieldId(json.getString("fieldId"));
        field.setLabel(json.getString("label"));
        field.setFieldType(json.getString("fieldType"));
        field.setRequired(json.getBoolean("required", false));
        return field;
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
