package com.creve.repository;

import com.creve.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Ye method us company ke saare reviews layega
    List<Review> findByCompanyId(Long companyId);
}