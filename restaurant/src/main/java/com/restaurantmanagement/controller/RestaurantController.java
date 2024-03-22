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

import com.restaurantmanagement.entity.Restaurant;
import com.restaurantmanagement.service.IRestaurantService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {
	
	private final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
	
	@Autowired
	private IRestaurantService restaurantService;
	
	
	@GetMapping
    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
            logger.info("Request all Restaurants");
        return restaurantService.getAllRestaurants(pageable);
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable("id") Long id){
        logger.info("Request restaurant with id: " + id);
        return restaurantService.getRestaurantById(id);
    }
    
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteRestaurantById(@PathVariable Long id){
        logger.info("Request restaurant delete with id: " + id);
        restaurantService.deleteRestaurantById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Restaurant saveRestaurant(@Valid @RequestBody Restaurant restaurant){
        logger.info("Request restaurant create with name: " + restaurant.getName());
        return restaurantService.saveRestaurant(restaurant);
    }

    @PutMapping
    public Restaurant updateRestaurant(@Valid @RequestBody Restaurant restaurant){
        return restaurantService.updateRestaurant(restaurant);
    }
    
    
    @GetMapping("/available/{isAvailable}")
    public Page<Restaurant> getfindByisAvailable(@PathVariable("isAvailable") String isAvailableString, Pageable pageable) {
        logger.info("Request restaurant with Status: " + isAvailableString);
        boolean isAvailable = Boolean.parseBoolean(isAvailableString); // Convertir String a boolean
        return restaurantService.findByisAvailable(isAvailable, pageable);
    }

}
