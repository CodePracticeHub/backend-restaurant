package com.restaurantmanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantmanagement.entity.Reservation;
import com.restaurantmanagement.entity.Reservation.ReservationStatus;
import com.restaurantmanagement.service.impl.ReservationServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/reservations")
public class ReservationController {
	
	
	private final ReservationServiceImpl reservationService;
	
	@Autowired
	public ReservationController(ReservationServiceImpl reservationService) {
		this.reservationService = reservationService;
	}
	
	private final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	
	@GetMapping()
	public List<Reservation> getAllReservations() {
		logger.info("Request for all reservations");
		return reservationService.getAllReservation();
	}
	
	@PostMapping()
	public Reservation addReservation(@RequestBody Reservation reservation) {
		logger.info("Request for adding reservation");
		reservation.setStatus(ReservationStatus.PENDING);
	    return reservationService.addReservation(reservation);
	}
	
	@GetMapping("/{id}")
	public Reservation getReservationById(@PathVariable Long id) {
	    logger.info("Request for reservation with id: "+ id);
	    return reservationService.getReservationById(id);
	} 
	
	@DeleteMapping("/{id}")
	public void deleteReservationById(@PathVariable Long id) {
	    logger.info("Request for delete reservation with id: "+ id);
	    reservationService.deleteReservationBy(id);
	}
    
	@PutMapping("/{id}")
	public Reservation updateReservationById(@PathVariable Long id, @RequestBody Reservation updatedReservation) {
		logger.info("Request for Reservation update");
		if(!id.equals(updatedReservation.getReservationId())) {
			throw new IllegalArgumentException("Path variable ID and request body ID do not match");
		}
		return reservationService.updateReservation(updatedReservation);
	}
}
