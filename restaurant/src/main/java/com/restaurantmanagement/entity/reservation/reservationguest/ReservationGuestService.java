package com.restaurantmanagement.entity.reservation.reservationguest;

import com.restaurantmanagement.entity.reservation.reservationguest.dto.ReservationGuestDTO;

import java.util.List;

public interface ReservationGuestService {
    ReservationGuestDTO createReservationGuest(ReservationGuestDTO reservationGuestDTO);
    ReservationGuestDTO getReservationGuestById(Long id);
    List<ReservationGuestDTO> getAllReservationGuests();
    ReservationGuestDTO updateReservationGuest(Long id, ReservationGuestDTO reservationGuestDTO);
    void deleteReservationGuest(Long id);
}

