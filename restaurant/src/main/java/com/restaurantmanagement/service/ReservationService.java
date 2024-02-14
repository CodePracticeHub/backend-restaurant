<<<<<<< Updated upstream
//package com.restaurantmanagement.service;
//
//import com.restaurantmanagement.entity.Reservation;
//
//import java.util.List;
//
//public interface ReservationService {
//
//
//}
=======
package com.restaurantmanagement.service;

import java.util.List;

import com.restaurantmanagement.entity.Reservation;

public interface ReservationService {
	
	List<Reservation> getAllReservation();
	
	Reservation addReservation(Reservation reservation);
	
	Reservation getReservationById(Long id);
	
	String deleteReservationBy(Long id); 
	
	Reservation updateReservation(Reservation reservation);
	
}
>>>>>>> Stashed changes
