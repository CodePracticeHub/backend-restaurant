package com.restaurantmanagement.restaurant.repos;

import com.restaurantmanagement.restaurant.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
