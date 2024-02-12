package com.restaurantmanagement.restaurant.repos;

import com.restaurantmanagement.restaurant.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
