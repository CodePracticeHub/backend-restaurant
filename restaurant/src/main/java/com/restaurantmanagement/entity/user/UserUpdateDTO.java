package com.restaurantmanagement.entity.user;

import com.restaurantmanagement.security.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateDTO {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
