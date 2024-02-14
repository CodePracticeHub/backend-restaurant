package com.restaurantmanagement.service;

import com.restaurantmanagement.entity.Menu;
import com.restaurantmanagement.repos.MenuRepository;
import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Page<Menu> getAllMenus(Pageable page) {
        return menuRepository.findAll(page);
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu is not found with id: " + id));
    }


    @Override
    public Menu saveMenuDetails(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public void deleteMenuById(Long id) {
        menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu is not found with id: " + id));
    }


    @Override
    public Menu updateMenuDetails(Long id, Menu menu) {
        Menu existingMenu = getMenuById(id);
        existingMenu.setName(menu.getName() != null ? menu.getName() : existingMenu.getName());
        existingMenu.setDescription(menu.getDescription() != null ? menu.getDescription() : existingMenu.getDescription());
        existingMenu.setPrice(menu.getPrice() != null ? menu.getPrice() : existingMenu.getPrice());
        existingMenu.setCategory(menu.getCategory() != null ? menu.getCategory() : existingMenu.getCategory());
        existingMenu.setImageURL(menu.getImageURL() != null ? menu.getImageURL() : existingMenu.getImageURL());
        existingMenu.setDate(menu.getDate() != null ? menu.getDate() : existingMenu.getDate());
        return menuRepository.save(existingMenu);
    }
}
