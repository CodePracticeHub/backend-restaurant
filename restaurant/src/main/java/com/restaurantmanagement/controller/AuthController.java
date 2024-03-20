package com.restaurantmanagement.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurantmanagement.security.jwt.JwtUtils;
import com.restaurantmanagement.security.model.ERole;
import com.restaurantmanagement.security.model.Role;
import com.restaurantmanagement.security.model.User;
import com.restaurantmanagement.security.payload.JwtResponse;
import com.restaurantmanagement.security.payload.MessageResponse;
import com.restaurantmanagement.security.repository.RoleRepository;
import com.restaurantmanagement.security.repository.UserRepository;
import com.restaurantmanagement.security.request.LoginRequest;
import com.restaurantmanagement.security.request.SetRoleRequest;
import com.restaurantmanagement.security.request.SignupRequest;
import com.restaurantmanagement.security.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getFirstname(),  signUpRequest.getLastname(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			for (String role : strRoles) {
				switch (role.toLowerCase()) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "employee":
					Role modRole = roleRepository.findByName(ERole.ROLE_EMPLOYE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				}
			}
			if (roles.size() == 0) {
				roles.add(new Role(ERole.ROLE_USER));
			}
		}
		
		user.setRoles(roles);
		
		roleRepository.saveAll(roles);
		
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
//	@PostMapping("/setrole")
//	public ResponseEntity<?> setRole(@Valid @RequestBody SetRoleRequest setRoleRequest){
//		User user=userRepository.findByUsername(setRoleRequest.getUsername()).get();
//		if (user!=null) {
//			
//			Set<String> strRoles = setRoleRequest.getRole();
//			Set<Role> roles = new HashSet<>();
//
//			if (strRoles == null) {
//				Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//				roles.add(userRole);
//			} else {
//				for (String role : strRoles) {
//					switch (role.toLowerCase()) {
//					case "admin":
//						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//						roles.add(adminRole);
//
//						break;
//					case "employee":
//						Role empRole = roleRepository.findByName(ERole.ROLE_EMPLOYE)
//								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//						roles.add(empRole);
//
//						break;
//					}
//				}			
//				
//		}
//			user.setRoles(roles);						
//			userRepository.save(user);
//			return ResponseEntity.ok(new MessageResponse("user role add successfully!"));
//			
//		}
//		
//		else {
//			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username dose dont exist!"));
//		}
//
//		
//		
//	}
	
	@PostMapping("/setrole")
	public ResponseEntity<?> setRole(@Valid @RequestBody SetRoleRequest setRoleRequest) {
	    User user = userRepository.findByUsername(setRoleRequest.getUsername()).orElse(null);

	    if (user == null) {
	        return ResponseEntity.badRequest().body(new MessageResponse("Error: Username does not exist!"));
	    }

	    Set<String> strRoles = setRoleRequest.getRole();
	    Set<Role> roles = new HashSet<>();

	    if (strRoles == null) {
	        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
	                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	        roles.add(userRole);
	    } else {
	        for (String role : strRoles) {
	            switch (role.toLowerCase()) {
	                case "admin":
	                    addRoleIfExists(ERole.ROLE_ADMIN, roles);
	                    break;
	                case "employee":
	                    addRoleIfExists(ERole.ROLE_EMPLOYE, roles);
	                    break;
	                case "user":
	                    addRoleIfExists(ERole.ROLE_USER, roles);
	                    break;
	               
	                
	                default:
	                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid role specified!"));
	            }
	        }
	    }

	    user.setRoles(roles);
	    userRepository.save(user);
	    return ResponseEntity.ok(new MessageResponse("User role added successfully!"));
	}

	private void addRoleIfExists(ERole roleName, Set<Role> roles) {
	    Role role = roleRepository.findByName(roleName)
	            .orElseThrow(() -> new RuntimeException("Error: Role is not found. MODEL"));
	    roles.add(role);
	}
}