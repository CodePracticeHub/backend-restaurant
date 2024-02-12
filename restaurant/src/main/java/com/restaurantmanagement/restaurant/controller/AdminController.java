package com.restaurantmanagement.restaurant.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/test")
    public String test() {
        logger.info("Received request for test");
        return "This is a test";
    }
}


