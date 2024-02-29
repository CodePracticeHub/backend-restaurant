package com.restaurantmanagement.controller;

import com.restaurantmanagement.entity.User;
import com.restaurantmanagement.entity.UserModel;
import com.restaurantmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    public int calculateFactorial(int number) {
        if (number <= 1) {
            return 1; // Base case: factorial of 1 is 1
        } else {
            return number * calculateFactorial(number - 1);
        }
    }

    @GetMapping
    public List<User> getAllUsers(Pageable pageable) {
        logger.info("Request all menus");
        int number = 1;
        calculateFactorial(number);

        return userService.getAllUsers(pageable).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> readUser(@PathVariable Long id){
        logger.info("Request to get user information with id: " + id);
        return new ResponseEntity<>(userService.readUser(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserModel user, @PathVariable Long id){
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        return new ResponseEntity<>(userService.updateUser(newUser, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

