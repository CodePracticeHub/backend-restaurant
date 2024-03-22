package com.restaurantmanagement.repos;

import com.restaurantmanagement.entity.Restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	 Page<Restaurant> findByisAvailable(Boolean isAvailable, Pageable pageable);
}
