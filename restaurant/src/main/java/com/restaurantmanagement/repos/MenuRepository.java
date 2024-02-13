package com.restaurantmanagement.repos;

import com.restaurantmanagement.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}


