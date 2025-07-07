package com.example.eyproject.controller;

import com.example.eyproject.model.Review;
import com.example.eyproject.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Add a new review
    @PostMapping("/add")
    public Review addReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    // Get all reviews
    @GetMapping("/all")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    // Get reviews by book name
    @GetMapping("/book")
    public List<Review> getReviewsByBookname(@RequestParam String bookname) {
        return reviewService.getReviewsByBookname(bookname);
    }

    // Get reviews by student email
    @GetMapping("/student")
    public List<Review> getReviewsByStudentEmail(@RequestParam String email) {
        return reviewService.getReviewsByStudentEmail(email);
    }

    // Delete a review by ID
    @DeleteMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        return reviewService.deleteReview(id);
    }
}
