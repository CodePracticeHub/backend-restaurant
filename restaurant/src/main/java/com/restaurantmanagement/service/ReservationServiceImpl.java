package com.restaurantmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantmanagement.entity.Reservation;
import com.restaurantmanagement.repos.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {
    
	@Autowired
	private ReservationRepository repo;
	
	@Override
	public List<Reservation> getAllReservation() {
	    return repo.findAll();
	}

	@Override
	public Reservation addReservation(Reservation reservation) {
		return repo.save(reservation);
	}

	@Override
	public Reservation getReservationById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteReservationBy(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation updateReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		return null;
	}

}
