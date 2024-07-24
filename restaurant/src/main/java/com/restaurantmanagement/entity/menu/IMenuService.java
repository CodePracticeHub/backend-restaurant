package com.restaurantmanagement.entity.menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface IMenuService {

    Page<Menu> getAllMenus(Pageable page);

    Menu getMenuById(Long id);

    void deleteMenuById(Long id);

    Menu saveMenuDetails(Menu menu);

    Menu updateMenuDetails(Long id, Menu menu);

    List<Menu> readByDate(Date startDate, Date endDate, Pageable page);
}
