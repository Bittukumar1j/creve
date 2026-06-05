package com.creve.controller;

import com.creve.model.Review;
import com.creve.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired private ReviewService reviewService;

    @PostMapping("/add")
    public String addReview(@RequestBody Review review) {
        reviewService.postReview(review);
        return "Review added successfully!";
    }
    @GetMapping("/all")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{companyId}")
    public List<Review> getReviews(@PathVariable Long companyId) {
        return reviewService.getReviewsByCompany(companyId);
    }
}
