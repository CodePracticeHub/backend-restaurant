package com.restaurantmanagement.entity.reservation.reservationguest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationGuestRepository extends JpaRepository<ReservationGuest, Long> {
}
