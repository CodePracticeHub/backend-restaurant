package com.restaurantmanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantmanagement.entity.Reservation;
import com.restaurantmanagement.service.ReservationServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/reservations")
public class ReservationController {
	
	@Autowired
	private ReservationServiceImpl reservationService;
	
	private final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	
	@GetMapping()
	public List<Reservation> getAllReservations() {
		logger.info("Request for all reservations");
		return reservationService.getAllReservation();
	}

}
