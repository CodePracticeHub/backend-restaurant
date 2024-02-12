package com.restaurantmanagement.restaurant.repos;

import com.restaurantmanagement.restaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}

