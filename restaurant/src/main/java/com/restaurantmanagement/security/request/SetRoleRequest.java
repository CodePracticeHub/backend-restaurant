package com.restaurantmanagement.security.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class SetRoleRequest {

	  @NotEmpty(message = "Username must not be empty")
	  private String username;

	  @NotEmpty(message = "Roles must not be empty")
	  private Set<String> role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
	  
	  

}
