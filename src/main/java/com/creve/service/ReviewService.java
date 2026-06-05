/*package com.creve.service;

import com.creve.model.Review;
import com.creve.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    @Autowired private ReviewRepository reviewRepository;

    public void postReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getReviewsByCompany(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }
}
*/

package com.creve.service;

import com.creve.model.Review;
import com.creve.repository.ReviewRepository;
import com.creve.repository.UserRepository;
import com.creve.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private UserRepository userRepository;     // Ye zaroori hai
    @Autowired private CompanyRepository companyRepository; // Ye zaroori hai

    public void postReview(Review review) {
        // ID se database se pura object uthao
        var user = userRepository.findById(review.getUser().getId()).orElseThrow();
        var company = companyRepository.findById(review.getCompany().getId()).orElseThrow();

        // Object set karo
        review.setUser(user);
        review.setCompany(company);

        // Ab save karo
        reviewRepository.save(review);
    }

    public List<Review> getReviewsByCompany(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}