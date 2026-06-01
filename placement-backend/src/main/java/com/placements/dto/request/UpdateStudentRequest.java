package com.placements.dto.request;

import io.vertx.core.json.JsonObject;

public class UpdateStudentRequest {

    private String rollNo;
    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String section;
    private String specialization;
    private String departmentName;
    private String personalEmail;
    private String batchCode;
    private Integer backlogs;
    private Boolean active;
    private Boolean freeze;
    private Boolean optedIn;
    private Double cgpa;

    public UpdateStudentRequest() {}

    public static UpdateStudentRequest fromJson(JsonObject json) {
        UpdateStudentRequest req = new UpdateStudentRequest();
        if (json.containsKey("rollNo")) req.setRollNo(json.getString("rollNo"));
        if (json.containsKey("firstName")) req.setFirstName(json.getString("firstName"));
        if (json.containsKey("lastName")) req.setLastName(json.getString("lastName"));
        if (json.containsKey("gender")) req.setGender(json.getString("gender"));
        if (json.containsKey("dob")) req.setDob(json.getString("dob"));
        if (json.containsKey("section")) req.setSection(json.getString("section"));
        if (json.containsKey("specialization")) req.setSpecialization(json.getString("specialization"));
        if (json.containsKey("departmentName")) req.setDepartmentName(json.getString("departmentName"));
        if (json.containsKey("personalEmail")) req.setPersonalEmail(json.getString("personalEmail"));
        if (json.containsKey("batchCode")) req.setBatchCode(json.getString("batchCode"));
        if (json.containsKey("backlogs")) req.setBacklogs(json.getInteger("backlogs"));
        if (json.containsKey("active")) req.setActive(json.getBoolean("active"));
        if (json.containsKey("freeze")) req.setFreeze(json.getBoolean("freeze"));
        if (json.containsKey("optedIn")) req.setOptedIn(json.getBoolean("optedIn"));
        if (json.containsKey("cgpa")) req.setCgpa(json.getDouble("cgpa"));
        return req;
    }

    public JsonObject toUpdateDocument() {
        JsonObject set = new JsonObject();
        if (rollNo != null) set.put("rollNo", rollNo);
        if (firstName != null) set.put("firstName", firstName);
        if (lastName != null) set.put("lastName", lastName);
        if (gender != null) set.put("gender", gender);
        if (dob != null) set.put("dob", dob);
        if (section != null) set.put("section", section);
        if (specialization != null) set.put("specialization", specialization);
        if (departmentName != null) set.put("departmentName", departmentName);
        if (personalEmail != null) set.put("personalEmail", personalEmail);
        if (batchCode != null) set.put("batchCode", batchCode);
        if (backlogs != null) set.put("backlogs", backlogs);
        if (active != null) set.put("active", active);
        if (freeze != null) set.put("freeze", freeze);
        if (optedIn != null) set.put("optedIn", optedIn);
        if (cgpa != null) set.put("cgpa", cgpa);
        return new JsonObject().put("$set", set);
    }

    public boolean isEmpty() {
        return rollNo == null && firstName == null && lastName == null && gender == null
                && dob == null && section == null && specialization == null
                && departmentName == null && personalEmail == null && batchCode == null
                && backlogs == null && active == null && freeze == null
                && optedIn == null && cgpa == null;
    }

    // Getters and Setters
    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getPersonalEmail() { return personalEmail; }
    public void setPersonalEmail(String personalEmail) { this.personalEmail = personalEmail; }

    public String getBatchCode() { return batchCode; }
    public void setBatchCode(String batchCode) { this.batchCode = batchCode; }

    public Integer getBacklogs() { return backlogs; }
    public void setBacklogs(Integer backlogs) { this.backlogs = backlogs; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Boolean getFreeze() { return freeze; }
    public void setFreeze(Boolean freeze) { this.freeze = freeze; }

    public Boolean getOptedIn() { return optedIn; }
    public void setOptedIn(Boolean optedIn) { this.optedIn = optedIn; }

    public Double getCgpa() { return cgpa; }
    public void setCgpa(Double cgpa) { this.cgpa = cgpa; }
}
