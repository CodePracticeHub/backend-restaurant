package com.restaurantmanagement.restaurant.repos;

import com.restaurantmanagement.restaurant.entity.Seating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatingRepository extends JpaRepository<Seating, Integer> {
}
