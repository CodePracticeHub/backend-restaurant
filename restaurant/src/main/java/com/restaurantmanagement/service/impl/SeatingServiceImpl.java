package com.restaurantmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.restaurantmanagement.entity.Seating;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import com.restaurantmanagement.repos.SeatingRepository;
import com.restaurantmanagement.service.ISeatingService;


@Service
public class SeatingServiceImpl implements ISeatingService{
	
	@Autowired
	private SeatingRepository seatingRepository;
	

	@Override
	public Page<Seating> getAllSeating(Pageable page) {
		return seatingRepository.findAll(page);
	}

	@Override
	public Seating getSeatingById(Long id) {
		return seatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seating is not found with id: " + id));
	}

	@Override
	public void deleteSeatingById(Long id) {
		seatingRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Seating is not found with id: " + id));
		
	}

	@Override
	public Seating saveSeating(Seating seating) {
		return seatingRepository.save(seating);
	}

	@Override
	public Seating updateSeating(Seating seating) {
		return seatingRepository.save(seating);
	}
	
	@Override
	public Page<Seating> findByisAvailable(Boolean isAvailable, Pageable page) {
		return seatingRepository.findByisAvailable(isAvailable, page);
	}

}
