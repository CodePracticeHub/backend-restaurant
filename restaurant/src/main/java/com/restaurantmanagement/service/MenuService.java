package com.restaurantmanagement.service;

import com.restaurantmanagement.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuService {

    Page<Menu> getAllMenus(Pageable page);

    Menu getMenuById(Long id);

    void deleteMenuById(Long id);

    Menu saveMenuDetails(Menu menu);

    Menu updateMenuDetails(Long id, Menu menu);
}


