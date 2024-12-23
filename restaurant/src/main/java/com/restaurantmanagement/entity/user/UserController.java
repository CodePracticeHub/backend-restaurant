package com.restaurantmanagement.entity.user;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.restaurantmanagement.security.model.User;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUser userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> getUserById(@PathVariable long userId) {
        try {
            User user = userService.getUserByUserId(userId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the user with ID: " + userId);
        }
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_EMPLOYEE') and !#updatedUser.roles.contains('ROLE_ADMIN'))")
    public ResponseEntity<?> updateUser(@PathVariable long userId, @Valid @RequestBody UserUpdateDTO updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        try {
            // Get the currently authenticated user's username
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Find the user by username
            User user = userService.getUserByUserName(username);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the current user data");
        }
    }


    // Uncomment and implement these endpoints as necessary
    // Endpoint for placing orders
    // @PostMapping("/orders")
    // @PreAuthorize("hasRole('ROLE_USER')")
    // public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
    //     // Logic to place order
    //     return ResponseEntity.ok("Order placed successfully");
    // }

    // Endpoint for making payments
    // @PostMapping("/payments")
    // @PreAuthorize("hasRole('ROLE_USER')")
    // public ResponseEntity<?> makePayment(@Valid @RequestBody PaymentRequest paymentRequest) {
    //     // Logic to make payment
    //     return ResponseEntity.ok("Payment made successfully");
    // }
}
