package com.placements.dto.response;

import com.placements.model.Student;
import io.vertx.core.json.JsonObject;

public class StudentResponse {

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

    public StudentResponse() {}

    public static StudentResponse fromStudent(Student student) {
        StudentResponse res = new StudentResponse();
        res.setId(student.getId());
        res.setRollNo(student.getRollNo());
        res.setFirstName(student.getFirstName());
        res.setLastName(student.getLastName());
        res.setGender(student.getGender());
        res.setDob(student.getDob());
        res.setSection(student.getSection());
        res.setSpecialization(student.getSpecialization());
        res.setDepartmentName(student.getDepartmentName());
        res.setPersonalEmail(student.getPersonalEmail());
        res.setBatchCode(student.getBatchCode());
        res.setBacklogs(student.getBacklogs());
        res.setActive(student.isActive());
        res.setFreeze(student.isFreeze());
        res.setOptedIn(student.isOptedIn());
        res.setCgpa(student.getCgpa());
        return res;
    }

    public static StudentResponse fromJson(JsonObject json) {
        return fromStudent(Student.fromJson(json));
    }

    public JsonObject toJson() {
        return new JsonObject()
                .put("_id", id)
                .put("rollNo", rollNo)
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("gender", gender)
                .put("dob", dob)
                .put("section", section)
                .put("specialization", specialization)
                .put("departmentName", departmentName)
                .put("personalEmail", personalEmail)
                .put("batchCode", batchCode)
                .put("backlogs", backlogs)
                .put("active", active)
                .put("freeze", freeze)
                .put("optedIn", optedIn)
                .put("cgpa", cgpa);
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

