package com.placements.dto.request;

import io.vertx.core.json.JsonObject;

public class CreateStudentRequest {

    private String id;
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
    private int backlogs;
    private boolean active;
    private boolean freeze;
    private boolean optedIn;
    private double cgpa;

    public CreateStudentRequest() {}

    public static CreateStudentRequest fromJson(JsonObject json) {
        CreateStudentRequest req = new CreateStudentRequest();
        req.setId(json.getString("_id"));
        req.setRollNo(json.getString("rollNo"));
        req.setFirstName(json.getString("firstName"));
        req.setLastName(json.getString("lastName"));
        req.setGender(json.getString("gender"));
        req.setDob(json.getString("dob"));
        req.setSection(json.getString("section"));
        req.setSpecialization(json.getString("specialization"));
        req.setDepartmentName(json.getString("departmentName"));
        req.setPersonalEmail(json.getString("personalEmail"));
        req.setBatchCode(json.getString("batchCode"));
        req.setBacklogs(json.getInteger("backlogs", 0));
        req.setActive(json.getBoolean("active", true));
        req.setFreeze(json.getBoolean("freeze", false));
        req.setOptedIn(json.getBoolean("optedIn", true));
        req.setCgpa(json.getDouble("cgpa", 0.0));
        return req;
    }

    public String validate() {
        if (id == null || id.isBlank()) return "_id is required";
        if (rollNo == null || rollNo.isBlank()) return "rollNo is required";
        if (firstName == null || firstName.isBlank()) return "firstName is required";
        if (lastName == null || lastName.isBlank()) return "lastName is required";
        if (gender == null || gender.isBlank()) return "gender is required";
        if (dob == null || dob.isBlank()) return "dob is required";
        if (section == null || section.isBlank()) return "section is required";
        if (specialization == null || specialization.isBlank()) return "specialization is required";
        if (departmentName == null || departmentName.isBlank()) return "departmentName is required";
        if (personalEmail == null || personalEmail.isBlank()) return "personalEmail is required";
        if (batchCode == null || batchCode.isBlank()) return "batchCode is required";
        return null;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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

    public int getBacklogs() { return backlogs; }
    public void setBacklogs(int backlogs) { this.backlogs = backlogs; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean isFreeze() { return freeze; }
    public void setFreeze(boolean freeze) { this.freeze = freeze; }

    public boolean isOptedIn() { return optedIn; }
    public void setOptedIn(boolean optedIn) { this.optedIn = optedIn; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }
}
