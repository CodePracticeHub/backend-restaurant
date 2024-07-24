package com.restaurantmanagement.entity.menu;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController("menuControllerEntity")
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final IMenuService menuService;
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public Page<Menu> getAllMenus(Pageable pageable) {
        logger.info("Request all menus");
        return menuService.getAllMenus(pageable);
    }

    @GetMapping("/{id}")
    public Menu getMenuById(@PathVariable("id") Long id) {
        logger.info("Request menu with id: " + id);
        return menuService.getMenuById(id);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteMenuById(@PathVariable Long id) {
        logger.info("Request delete with id: " + id);
        menuService.deleteMenuById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public Menu saveMenuDetails(@Valid @RequestBody Menu menu) {
        logger.info("Request create with id: " + menu);
        return menuService.saveMenuDetails(menu);
    }

    @PutMapping("/{id}")
    public Menu updateMenuDetails(@Valid @RequestBody Menu menu, @PathVariable Long id) {
        logger.info("Request update menu with id: " + id);
        return menuService.updateMenuDetails(id, menu);
    }

    @GetMapping("/search/date")
    public List<Menu> getMenuByDate(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            Pageable page) {
        logger.info("Request menus by dates from: " + startDate + " : " + endDate);

        // Convert java.util.Date to java.sql.Date if necessary
        Date sqlStartDate = startDate != null ? new Date(startDate.getTime()) : null;
        Date sqlEndDate = endDate != null ? new Date(endDate.getTime()) : null;

        return menuService.readByDate(sqlStartDate, sqlEndDate, page);
    }
}
