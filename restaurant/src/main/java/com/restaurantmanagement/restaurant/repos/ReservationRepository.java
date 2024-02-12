package com.restaurantmanagement.restaurant.repos;

import com.restaurantmanagement.restaurant.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
