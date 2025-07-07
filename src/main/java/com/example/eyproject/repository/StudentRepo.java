package com.example.eyproject.repository;

import com.example.eyproject.model.Student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
    
    Optional<Student> findByEmail(String email);   // Used for login or email lookup

    boolean existsByEmail(String email);           // Used to prevent duplicate registration
}
