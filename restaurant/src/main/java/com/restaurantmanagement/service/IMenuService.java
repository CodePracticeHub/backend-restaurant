package com.restaurantmanagement.service;

import com.restaurantmanagement.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IMenuService {

    Page<Menu> getAllMenus(Pageable page);

    Menu getMenuById(Long id);

    void deleteMenuById(Long id);

    Menu saveMenuDetails(Menu menu);

    Menu updateMenuDetails(Long id, Menu menu);

    List<Menu> readByDate(Date startDate, Date endDate, Pageable page);
}


