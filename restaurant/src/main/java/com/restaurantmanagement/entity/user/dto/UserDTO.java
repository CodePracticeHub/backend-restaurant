package com.restaurantmanagement.entity.user.dto;

import com.restaurantmanagement.security.model.User;
import com.restaurantmanagement.security.model.Role;
import lombok.Data;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
