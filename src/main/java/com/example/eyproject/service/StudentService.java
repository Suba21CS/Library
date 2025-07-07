package com.example.eyproject.service;

import com.example.eyproject.model.Student;
import com.example.eyproject.repository.StudentRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public Student registerStudent(Student student) {
        return studentRepo.save(student);
    }

    public boolean emailExists(String email) {
        return studentRepo.existsByEmail(email);
    }

    public Optional<Student> login(String email, String password) {
        Optional<Student> student = studentRepo.findByEmail(email);
        if (student.isPresent() && student.get().getPassword().equals(password)) {
            return student;
        }
        return Optional.empty();
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }
}
