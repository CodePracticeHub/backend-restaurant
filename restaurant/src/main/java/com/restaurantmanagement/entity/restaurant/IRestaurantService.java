package com.restaurantmanagement.entity.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantService {
	
	Page<Restaurant> getAllRestaurants(Pageable page);

    Restaurant getRestaurantById(Long id);

    void deleteRestaurantById(Long id);

    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant updateRestaurant(Restaurant restaurant);
    
    Page<Restaurant> findByisAvailable(Boolean isAvailable,Pageable page);

}
