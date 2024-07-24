package com.restaurantmanagement.entity.user;

import java.util.List;

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
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(long userId, UserUpdateDTO updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        if (updatedUser.getFirstname() != null) {
            existingUser.setFirstname(updatedUser.getFirstname());
        }
        if (updatedUser.getLastname() != null) {
            existingUser.setLastname(updatedUser.getLastname());
        }
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getRoles() != null && !updatedUser.getRoles().isEmpty()) {
            existingUser.setRoles(updatedUser.getRoles());
        }

        return userRepository.save(existingUser);
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
