package com.restaurantmanagement.repos;

import com.restaurantmanagement.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Page<Menu> findByNameContainingIgnoreCase(@RequestParam("name")
                                              String name, Pageable pageable);
    Page<Menu> findByCategoryIgnoreCase(@RequestParam("category")
                                        String category, Pageable pageable);
    Page<Menu> findByDateBetween(Date startDate, Date endDate, Pageable page);
}



