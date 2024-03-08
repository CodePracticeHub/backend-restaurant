package com.restaurantmanagement.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.restaurantmanagement.entity.Restaurant;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import com.restaurantmanagement.repos.RestaurantRepository;
import com.restaurantmanagement.service.IRestaurantService;

@Service
public class RestaurantServiceImpl implements IRestaurantService{
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	

	@Override
	public Page<Restaurant> getAllRestaurants(Pageable page) {
		return restaurantRepository.findAll(page);
	}

	@Override
	public Restaurant getRestaurantById(Long id) {
		return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resturant is not found with id: " + id));
	}

	@Override
	public void deleteRestaurantById(Long id) {
		restaurantRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Restaurant is not found with id: " + id));
	}

	@Override
	public Restaurant saveRestaurant(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}

	@Override
	public Page<Restaurant> findByisAvailable(Boolean isAvailable, Pageable page) {
		return restaurantRepository.findByisAvailable(isAvailable, page);
	}

}
