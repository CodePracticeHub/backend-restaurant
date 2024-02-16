
package com.restaurantmanagement.repos;

import com.restaurantmanagement.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
}

