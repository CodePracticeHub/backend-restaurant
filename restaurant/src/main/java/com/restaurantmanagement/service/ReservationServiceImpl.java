package com.restaurantmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantmanagement.entity.Reservation;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
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
		Optional<Reservation> optional = repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return optional.orElseThrow(() -> new ResourceNotFoundException("Reservation Not Found!"));
		}
	}

	@Override
	public void deleteReservationBy(Long id) {
		Optional<Reservation> optional = repo.findById(id);
		if(optional.isPresent()) {
			Reservation reservation = optional.get();
			repo.delete(reservation);
		}
	}

	@Override
	public Reservation updateReservation(Reservation reservation) {
		return repo.save(reservation);
	}

}
