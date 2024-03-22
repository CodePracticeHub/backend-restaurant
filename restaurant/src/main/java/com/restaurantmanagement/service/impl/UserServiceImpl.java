package com.restaurantmanagement.service.impl;

import java.util.List;

import com.restaurantmanagement.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurantmanagement.security.model.User;
import com.restaurantmanagement.security.repository.UserRepository;
@Service
public class UserServiceImpl implements IUser {
	private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserByUserId(long userId) {
    	
        return userRepository.findById(userId);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId);
        if (existingUser != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            
            userRepository.save(existingUser);
        }
        else {
        	throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        return existingUser;
    }
    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}

