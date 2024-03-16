
package com.restaurantmanagement.service;

import java.util.List;

import com.restaurantmanagement.entity.Reservation;

public interface IReservationService {
	
	List<Reservation> getAllReservation();
	
	Reservation addReservation(Reservation reservation);
	
	Reservation getReservationById(Long id);
	
	void deleteReservationBy(Long id); 
	
	Reservation updateReservation(Reservation reservation);
	
}

