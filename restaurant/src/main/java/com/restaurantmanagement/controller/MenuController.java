package com.restaurantmanagement.controller;

import com.restaurantmanagement.entity.Menu;
import com.restaurantmanagement.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    public int calculateFactorial(int number) {
        if (number <= 1) {
            return 1; // Base case: factorial of 1 is 1
        } else {
            return number * calculateFactorial(number - 1);
        }
    }

    @GetMapping
    public Page<Menu> getAllMenus(Pageable pageable) {
            logger.info("Request all menus pageable");
        int number = 1;
        calculateFactorial(number);

        return menuService.getAllMenus(pageable);
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable("id") Long id){
        logger.info("Request menu with id: " + id);
        return menuService.getMenuById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteMenuById(@RequestParam Long id){
        logger.info("Request delete with id: " + id);
        menuService.deleteMenuById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Menu saveMenuDetails(@RequestBody Menu menu){
        logger.info("Request create with id: " + menu);
        return menuService.saveMenuDetails(menu);
    }

    @PutMapping("/{id}")
    public Menu updateMenuDetails(@RequestBody Menu menu, @PathVariable Long id){
        logger.info("Request update menu with id: " + id);
        return menuService.updateMenuDetails(id, menu);
    }


}
