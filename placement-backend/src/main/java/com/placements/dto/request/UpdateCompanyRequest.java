package com.placements.dto.request;

import io.vertx.core.json.JsonObject;

public class UpdateCompanyRequest {

    private String companyCode;
    private String companyName;
    private String industry;
    private String contactPerson;
    private String email;
    private Long phone;            // nullable — only set if present in request
    private String address;

    public UpdateCompanyRequest() {}

    public static UpdateCompanyRequest fromJson(JsonObject json) {
        UpdateCompanyRequest req = new UpdateCompanyRequest();
        if (json.containsKey("companyCode"))   req.setCompanyCode(json.getString("companyCode"));
        if (json.containsKey("companyName"))   req.setCompanyName(json.getString("companyName"));
        if (json.containsKey("industry"))      req.setIndustry(json.getString("industry"));
        if (json.containsKey("contactPerson")) req.setContactPerson(json.getString("contactPerson"));
        if (json.containsKey("email"))         req.setEmail(json.getString("email"));
        if (json.containsKey("address"))       req.setAddress(json.getString("address"));

        if (json.containsKey("phone")) {
            Object phoneField = json.getValue("phone");
            if (phoneField instanceof JsonObject) {
                Object numObj = ((JsonObject) phoneField).getValue("$numberLong");
                String raw = null;
                if (numObj instanceof String) {
                    raw = (String) numObj;
                } else if (numObj instanceof Number) {
                    raw = String.valueOf(numObj);
                }
                if (raw != null) req.setPhone(Long.parseLong(raw));
            } else if (phoneField instanceof Number) {
                req.setPhone(((Number) phoneField).longValue());
            } else if (phoneField instanceof String) {
                try { req.setPhone(Long.parseLong((String) phoneField)); } catch (NumberFormatException ignored) {}
            }
        }

        return req;
    }
    public JsonObject toUpdateDocument() {
        JsonObject set = new JsonObject();
        if (companyCode != null)   set.put("companyCode", companyCode);
        if (companyName != null)   set.put("companyName", companyName);
        if (industry != null)      set.put("industry", industry);
        if (contactPerson != null) set.put("contactPerson", contactPerson);
        if (email != null)         set.put("email", email);
        if (phone != null)         set.put("phone", phone);
        if (address != null)       set.put("address", address);
        return new JsonObject().put("$set", set);
    }

    public boolean isEmpty() {
        return companyCode == null && companyName == null && industry == null
                && contactPerson == null && email == null && phone == null && address == null;
    }

    // Getters and Setters
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

    public Long getPhone() { return phone; }
    public void setPhone(Long phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
