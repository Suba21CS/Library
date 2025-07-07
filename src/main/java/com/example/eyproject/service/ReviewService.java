package com.example.eyproject.service;

import com.example.eyproject.model.Review;
import com.example.eyproject.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    // Save review
    public Review saveReview(Review review) {
        return reviewRepo.save(review);
    }

    // Get all reviews
    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    // Get reviews by book name
    public List<Review> getReviewsByBookname(String bookname) {
        return reviewRepo.findByBookname(bookname);
    }

    // Get reviews by student email
    public List<Review> getReviewsByStudentEmail(String email) {
        return reviewRepo.findByStudentEmail(email);
    }

    // Delete review by ID
    public String deleteReview(Long id) {
        reviewRepo.deleteById(id);
        return "Review deleted with id: " + id;
    }
}
