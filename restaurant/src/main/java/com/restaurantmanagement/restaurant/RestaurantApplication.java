package com.restaurantmanagement.restaurant;

import com.restaurantmanagement.restaurant.entity.Admin;
import com.restaurantmanagement.restaurant.entity.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantApplication {



    public static void main(String[] args) {
        Admin admin = new Admin();
        SpringApplication.run(RestaurantApplication.class, args);
    }

}
