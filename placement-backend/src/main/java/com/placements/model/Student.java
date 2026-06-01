package com.placements.model;

import io.vertx.core.json.JsonObject;

public class Student {

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

    public Student() {}

    public static Student fromJson(JsonObject json) {
        Student s = new Student();
        s.setId(json.getString("_id"));
        s.setRollNo(json.getString("rollNo"));
        s.setFirstName(json.getString("firstName"));
        s.setLastName(json.getString("lastName"));
        s.setGender(json.getString("gender"));
        s.setDob(json.getString("dob"));
        s.setSection(json.getString("section"));
        s.setSpecialization(json.getString("specialization"));
        s.setDepartmentName(json.getString("departmentName"));
        s.setPersonalEmail(json.getString("personalEmail"));
        s.setBatchCode(json.getString("batchCode"));
        s.setBacklogs(json.getInteger("backlogs", 0));
        s.setActive(json.getBoolean("active", true));
        s.setFreeze(json.getBoolean("freeze", false));
        s.setOptedIn(json.getBoolean("optedIn", true));
        s.setCgpa(json.getDouble("cgpa", 0.0));
        return s;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (id != null) json.put("_id", id);
        json.put("rollNo", rollNo);
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("gender", gender);
        json.put("dob", dob);
        json.put("section", section);
        json.put("specialization", specialization);
        json.put("departmentName", departmentName);
        json.put("personalEmail", personalEmail);
        json.put("batchCode", batchCode);
        json.put("backlogs", backlogs);
        json.put("active", active);
        json.put("freeze", freeze);
        json.put("optedIn", optedIn);
        json.put("cgpa", cgpa);
        return json;
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
