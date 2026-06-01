package com.placements.service;

import com.placements.dto.request.CreateStudentRequest;
import com.placements.dto.request.UpdateStudentRequest;
import com.placements.dto.response.StudentResponse;
import com.placements.model.Student;
import com.placements.repository.StudentRepository;
import io.vertx.core.Future;

import java.util.List;
import java.util.stream.Collectors;

public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Future<List<StudentResponse>> getAllStudents() {
        return studentRepository.findAll()
                .map(students -> students.stream()
                        .map(StudentResponse::fromStudent)
                        .collect(Collectors.toList()));
    }

    public Future<StudentResponse> getStudentById(String id) {
        return studentRepository.findById(id)
                .map(student -> {
                    if (student == null) {
                        throw new RuntimeException("Student not found with id: " + id);
                    }
                    return StudentResponse.fromStudent(student);
                });
    }

    public Future<StudentResponse> createStudent(CreateStudentRequest request) {
        Student student = new Student();
        student.setId(request.getId());
        student.setRollNo(request.getRollNo());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setGender(request.getGender());
        student.setDob(request.getDob());
        student.setSection(request.getSection());
        student.setSpecialization(request.getSpecialization());
        student.setDepartmentName(request.getDepartmentName());
        student.setPersonalEmail(request.getPersonalEmail());
        student.setBatchCode(request.getBatchCode());
        student.setBacklogs(request.getBacklogs());
        student.setActive(request.isActive());
        student.setFreeze(request.isFreeze());
        student.setOptedIn(request.isOptedIn());
        student.setCgpa(request.getCgpa());

        return studentRepository.create(student)
                .map(StudentResponse::fromStudent);
    }

    public Future<StudentResponse> updateStudent(String id, UpdateStudentRequest request) {
        if (request.isEmpty()) {
            return Future.failedFuture("No fields provided for update");
        }
        return studentRepository.update(id, request.toUpdateDocument())
                .map(updated -> {
                    if (updated == null) {
                        throw new RuntimeException("Student not found with id: " + id);
                    }
                    return StudentResponse.fromStudent(updated);
                });
    }

    public Future<Void> deleteStudent(String id) {
        return studentRepository.delete(id)
                .compose(deleted -> {
                    if (!deleted) {
                        return Future.failedFuture("Student not found with id: " + id);
                    }
                    return Future.succeededFuture();
                });
    }
}

