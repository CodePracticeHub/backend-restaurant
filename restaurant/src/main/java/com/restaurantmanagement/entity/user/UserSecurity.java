package com.restaurantmanagement.entity.user;

import com.restaurantmanagement.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    @Autowired
    private IUser userService;

    public boolean isNotAdmin(Authentication authentication, long userId) {
        User currentUser = (User) authentication.getPrincipal();
        return userService.getUserByUserId(userId).getRoles().stream().noneMatch(role -> role.getName().equals("ROLE_ADMIN"));
    }
}
