package com.example.interview.repositories;

import com.example.interview.models.ProductRating;
import com.example.interview.models.ProductRatingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRatingRepository extends JpaRepository<ProductRating, ProductRatingId> {
}
