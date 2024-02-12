package com.restaurantmanagement.restaurant.repos;

import com.restaurantmanagement.restaurant.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
