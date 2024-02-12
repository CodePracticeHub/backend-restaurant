package com.restaurantmanagement.restaurant.repos;

import com.restaurantmanagement.restaurant.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
