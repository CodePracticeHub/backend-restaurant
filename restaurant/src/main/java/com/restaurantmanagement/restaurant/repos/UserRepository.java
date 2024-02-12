package com.restaurantmanagement.restaurant.repos;

import com.restaurantmanagement.restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
