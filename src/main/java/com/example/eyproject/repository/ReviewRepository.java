package com.example.eyproject.repository;

import com.example.eyproject.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookname(String bookname);
    List<Review> findByStudentEmail(String studentEmail);
}
