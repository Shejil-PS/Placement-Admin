package com.placements.dto.request;

import io.vertx.core.json.JsonObject;

public class CreateCompanyRequest {

    private String id;
    private String companyCode;
    private String companyName;
    private String industry;
    private String contactPerson;
    private String email;
    private long phone;
    private String address;

    public CreateCompanyRequest() {}

    public static CreateCompanyRequest fromJson(JsonObject json) {
        CreateCompanyRequest req = new CreateCompanyRequest();
        req.setId(json.getString("_id"));
        req.setCompanyCode(json.getString("companyCode"));
        req.setCompanyName(json.getString("companyName"));
        req.setIndustry(json.getString("industry"));
        req.setContactPerson(json.getString("contactPerson"));
        req.setEmail(json.getString("email"));

        // Accept phone as { "$numberLong": "..." }, a plain long, or a plain string
        Object phoneField = json.getValue("phone");
        if (phoneField instanceof JsonObject) {
            Object numObj = ((JsonObject) phoneField).getValue("$numberLong");
            String raw = null;
            if (numObj instanceof String) {
                raw = (String) numObj;
            } else if (numObj instanceof Number) {
                raw = String.valueOf(numObj);
            }
            req.setPhone(raw != null ? Long.parseLong(raw) : 0L);
        } else if (phoneField instanceof Number) {
            req.setPhone(((Number) phoneField).longValue());
        } else if (phoneField instanceof String) {
            try { req.setPhone(Long.parseLong((String) phoneField)); } catch (NumberFormatException ignored) {}
        }

        req.setAddress(json.getString("address"));
        return req;
    }

    public String validate() {
        if (id == null || id.isBlank()) return "_id is required";
        if (companyCode == null || companyCode.isBlank()) return "companyCode is required";
        if (companyName == null || companyName.isBlank()) return "companyName is required";
        if (industry == null || industry.isBlank()) return "industry is required";
        if (contactPerson == null || contactPerson.isBlank()) return "contactPerson is required";
        if (email == null || email.isBlank()) return "email is required";
        if (phone <= 0) return "phone is required and must be a positive number";
        if (address == null || address.isBlank()) return "address is required";
        return null;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCompanyCode() { return companyCode; }
    public void setCompanyCode(String companyCode) { this.companyCode = companyCode; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getPhone() { return phone; }
    public void setPhone(long phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
