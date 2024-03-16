//package com.restaurantmanagement.service;
//
//import com.restaurantmanagement.entity.User;
//import com.restaurantmanagement.entity.UserModel;
//import com.restaurantmanagement.exceptions.AlreadyExistException;
//import com.restaurantmanagement.exceptions.ResourceNotFoundException;
//import com.restaurantmanagement.repos.UserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.BeanUtils;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import java.util.regex.Pattern;
//
//@Service
//@AllArgsConstructor
//public class UserServiceImpl implements UserServiceImpl {
//
//    private UserRepository userRepository;
//    private PasswordEncoder bcryptEncoder;
//    @Override
//    public Page<User> getAllUsers(Pageable page) {
//        return userRepository.findAll(page);
//    }
//
//    @Override
//    public User createUser(UserModel user) {
//        if (!isValidEmail(user.getEmail())) {
//            throw new IllegalArgumentException("Invalid email format: " + user.getEmail());
//        }
//
//        if (userRepository.existsByEmail(user.getEmail())) {
//            throw new AlreadyExistException("User with email already exists: " + user.getEmail());
//        }
//
//        User newUser = new User();
//        BeanUtils.copyProperties(user, newUser);
//        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//        return userRepository.save(newUser);
//    }
//
//    private boolean isValidEmail(String email) {
//        if (StringUtils.isEmpty(email)) {
//            return false;
//        }
//
//        // Simple email format validation using regex
//        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
//        Pattern pattern = Pattern.compile(emailRegex);
//
//        return pattern.matcher(email).matches();
//    }
//
//    @Override
//    public User readUser(Long id) {
//        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
//    }
//
//    @Override
//    public User updateUser(User user, Long id) {
//        User existingUser = readUser(id);
//        existingUser.setFirstName(user.getFirstName() != null ? user.getFirstName() : existingUser.getFirstName());
//        existingUser.setLastName(user.getLastName() != null ? user.getLastName() : existingUser.getLastName());
//        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
//        existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
//
//        return userRepository.save(existingUser);
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
//        userRepository.delete(existingUser);
//    }
//}
