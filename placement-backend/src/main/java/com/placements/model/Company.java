package com.placements.model;

import io.vertx.core.json.JsonObject;

public class Company {

    private String id;            // "_id" → plain String e.g. "C001"
    private String companyCode;
    private String companyName;
    private String industry;
    private String contactPerson;
    private String email;
    private long phone;            // "$numberLong" stored as long
    private String address;

    public Company() {}

    public static Company fromJson(JsonObject json) {
        Company c = new Company();
        c.setId(json.getString("_id"));
        c.setCompanyCode(json.getString("companyCode"));
        c.setCompanyName(json.getString("companyName"));
        c.setIndustry(json.getString("industry"));
        c.setContactPerson(json.getString("contactPerson"));
        c.setEmail(json.getString("email"));

        // phone is stored as { "$numberLong": "9876543210" }
        Object phoneField = json.getValue("phone");
        if (phoneField instanceof JsonObject) {
            String raw = ((JsonObject) phoneField).getString("$numberLong");
            c.setPhone(raw != null ? Long.parseLong(raw) : 0L);
        } else if (phoneField instanceof Number) {
            c.setPhone(((Number) phoneField).longValue());
        }

        c.setAddress(json.getString("address"));
        return c;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (id != null) json.put("_id", id);
        json.put("companyCode", companyCode);
        json.put("companyName", companyName);
        json.put("industry", industry);
        json.put("contactPerson", contactPerson);
        json.put("email", email);
        json.put("phone", phone);
        json.put("address", address);
        return json;
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
