package com.restaurantmanagement.entity.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Page<Menu> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Menu> findByCategoryIgnoreCase(String category, Pageable pageable);

    Page<Menu> findByDateBetween(Date startDate, Date endDate, Pageable page);
}
