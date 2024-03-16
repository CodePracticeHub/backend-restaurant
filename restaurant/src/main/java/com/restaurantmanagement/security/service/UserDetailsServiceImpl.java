package com.restaurantmanagement.security.service;

import com.restaurantmanagement.security.model.ERole;
import com.restaurantmanagement.security.model.Role;
import com.restaurantmanagement.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurantmanagement.security.model.User;
import com.restaurantmanagement.security.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

//  @Autowired
//  private PasswordEncoder passwordEncoder;
//  @Autowired
//  private RoleRepository roleRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

  // Method to register a new user with default role ROLE_USER
//  @Transactional
//  public User registerNewUser(String firstName, String lastName, String username, String email, String password) {
//    User newUser = new User(firstName, lastName, username, email, passwordEncoder.encode(password));
//
//    // Assign default role ROLE_USER
//    Optional<Role> defaultRole = roleRepository.findByName(ERole.valueOf("ROLE_USER"));
//    Set<Role> roles = new HashSet<>();
//    roles.add(null);
//    newUser.setRoles(roles);
//
//    return userRepository.save(newUser);
//  }
}
