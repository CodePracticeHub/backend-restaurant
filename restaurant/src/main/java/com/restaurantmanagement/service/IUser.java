package com.restaurantmanagement.service;

import java.util.Collection;
import java.util.List;

import com.restaurantmanagement.security.model.User;





public interface IUser {

	 List<User> getUsers();

	    void deleteUser(long userId);

	    User getUserByUserId(long userId);

	    User getUserByUserName(String userName);

	    User updateUser(long userId, User updatedUser);
}
