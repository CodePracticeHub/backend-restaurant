package com.restaurantmanagement.service.impl;

import java.util.List;
import java.util.Optional;

import com.restaurantmanagement.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantmanagement.entity.Reservation;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import com.restaurantmanagement.repos.ReservationRepository;

@Service
public class ReservationServiceImpl implements IReservationService {
    
	
	private final ReservationRepository repo;
	
	@Autowired
	public ReservationServiceImpl(ReservationRepository repo) {
		this.repo = repo;
	}
	
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
	public Reservation updateReservation(Reservation updatedReservation) {
		Long reservationId = updatedReservation.getReservationId();

		Reservation existingReservation = repo.findById(reservationId)
				.orElseThrow(() -> new ResourceNotFoundException("Reservation with ID " +reservationId+ " not found."));

		existingReservation.setCustomerName(updatedReservation.getCustomerName());
		existingReservation.setCustomerEmail(updatedReservation.getCustomerEmail());
		existingReservation.setCustomerPhone(updatedReservation.getCustomerPhone());
		existingReservation.setReservationDateTime(updatedReservation.getReservationDateTime());
		existingReservation.setNumberOfPeople(updatedReservation.getNumberOfPeople());
		existingReservation.setTableNumber(updatedReservation.getTableNumber());
		existingReservation.setStatus(updatedReservation.getStatus());
		existingReservation.setSpecialRequest(updatedReservation.getSpecialRequest());

		return repo.save(existingReservation);
	}

}
