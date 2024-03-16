package com.restaurantmanagement.controller;
//package com.restaurantmanagement.controller;
//
//import com.restaurantmanagement.entity.User;
//import com.restaurantmanagement.entity.UserModel;
//import com.restaurantmanagement.service.impl.UserServiceImpl;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/api/v1")
//public class AuthController {
//
//    private final Logger logger = LoggerFactory.getLogger(UserController.class);
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<User> save(@Valid @RequestBody UserModel user){
//        logger.info("Request to create a user: " + user);
//        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login() {
//        return new ResponseEntity<String>("User is logged in", HttpStatus.OK);
//    }
//}
