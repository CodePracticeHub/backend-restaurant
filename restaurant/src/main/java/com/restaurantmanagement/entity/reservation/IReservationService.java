
package com.restaurantmanagement.entity.reservation;

import java.util.List;

public interface IReservationService {
	
	List<Reservation> getAllReservation();
	
	Reservation addReservation(Reservation reservation);
	
	Reservation getReservationById(Long id);
	
	void deleteReservationBy(Long id); 
	
	Reservation updateReservation(Reservation reservation);
	
}

