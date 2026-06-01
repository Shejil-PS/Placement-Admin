package com.placements.dto.response;

import com.placements.model.Company;
import io.vertx.core.json.JsonObject;

public class CompanyResponse {

    private String id;
    private String companyCode;
    private String companyName;
    private String industry;
    private String contactPerson;
    private String email;
    private long phone;
    private String address;

    public CompanyResponse() {}

    public static CompanyResponse fromCompany(Company company) {
        CompanyResponse res = new CompanyResponse();
        res.setId(company.getId());
        res.setCompanyCode(company.getCompanyCode());
        res.setCompanyName(company.getCompanyName());
        res.setIndustry(company.getIndustry());
        res.setContactPerson(company.getContactPerson());
        res.setEmail(company.getEmail());
        res.setPhone(company.getPhone());
        res.setAddress(company.getAddress());
        return res;
    }

    public static CompanyResponse fromJson(JsonObject json) {
        return fromCompany(Company.fromJson(json));
    }

    public JsonObject toJson() {
        return new JsonObject()
                .put("_id", id)
                .put("companyCode", companyCode)
                .put("companyName", companyName)
                .put("industry", industry)
                .put("contactPerson", contactPerson)
                .put("email", email)
                .put("phone", phone)
                .put("address", address);
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

