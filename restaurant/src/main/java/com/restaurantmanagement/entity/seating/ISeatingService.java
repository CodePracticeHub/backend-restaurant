package com.restaurantmanagement.entity.seating;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISeatingService {
	
	Page<Seating> getAllSeating(Pageable page);

	Seating getSeatingById(Long id);

    void deleteSeatingById(Long id);

    Seating saveSeating(Seating seating);

    Seating updateSeating(Seating seating);
    
    Page<Seating> findByisAvailable(Boolean isAvailable,Pageable page);

}
