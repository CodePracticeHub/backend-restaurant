package com.restaurantmanagement.entity.menu;

import com.restaurantmanagement.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

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
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu is not found with id: " + id));

        menuRepository.delete(menu);
    }

    @Override
    public Menu updateMenuDetails(Long id, Menu menu) {
        Menu existingMenu = getMenuById(id);
        existingMenu.setName(menu.getName() != null ? menu.getName() : existingMenu.getName());
        existingMenu.setDescription(menu.getDescription() != null ? menu.getDescription() : existingMenu.getDescription());
        existingMenu.setPrice(menu.getPrice() != null ? menu.getPrice() : existingMenu.getPrice());
        existingMenu.setCategory(menu.getCategory() != null ? menu.getCategory() : existingMenu.getCategory());
        existingMenu.setImageURL(menu.getImageURL() != null ? menu.getImageURL() : existingMenu.getImageURL());

        // Convert java.util.Date to java.sql.Date if necessary
        existingMenu.setDate(menu.getDate() != null ? new Date(menu.getDate().getTime()) : existingMenu.getDate());

        return menuRepository.save(existingMenu);
    }

    @Override
    public List<Menu> readByDate(Date startDate, Date endDate, Pageable page) {
        if (startDate == null) {
            startDate = new Date(0); // January 1, 1970
        }
        if (endDate == null) {
            endDate = new Date(System.currentTimeMillis()); // Current timestamp
        }
        return menuRepository.findByDateBetween(startDate, endDate, page).toList();
    }
}
