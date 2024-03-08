package com.restaurantmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantmanagement.entity.Seating;
import com.restaurantmanagement.service.ISeatingService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/seating")
public class SeatingController {
	
	private final Logger logger = LoggerFactory.getLogger(SeatingController.class);
	
	@Autowired
	private ISeatingService seatingService;
	
	@GetMapping
    public Page<Seating> getAllSeating(Pageable pageable) {
            logger.info("Request all Seating");
        return seatingService.getAllSeating(pageable);
    }

    @GetMapping("/{id}")
    public Seating getRestaurantById(@PathVariable("id") Long id){
        logger.info("Request seating with id: " + id);
        return seatingService.getSeatingById(id);
    }
    
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteSeatingById(@PathVariable Long id){
        logger.info("Request seating delete with id: " + id);
        seatingService.deleteSeatingById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Seating saveSeating(@Valid @RequestBody Seating seating){
        logger.info("Request seating create with desc: " + seating.getPositionDescription());
        return seatingService.saveSeating(seating);
    }

    @PutMapping
    public Seating updateSeating(@Valid @RequestBody Seating seating){
    	logger.info("Request seating update with name: " + seating.getSeatingId());
        return seatingService.updateSeating(seating);
    }
    
    @GetMapping("/available/{isAvailable}")
    public Page<Seating> getfindByisAvailable(@PathVariable("isAvailable") String isAvailableString, Pageable pageable) {
        logger.info("Request restaurant with Status: " + isAvailableString);
        boolean isAvailable = Boolean.parseBoolean(isAvailableString); // Convertir String a boolean
        return seatingService.findByisAvailable(isAvailable, pageable);
    }

}
