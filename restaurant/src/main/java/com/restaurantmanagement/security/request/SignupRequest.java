package com.restaurantmanagement.security.request;

import java.util.Set;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SignupRequest {

  @NotBlank
  @Size(min = 3, max = 20)
  private String firstname;

  @NotBlank
  @Size(min = 3, max = 20)
  private String lastname;
  
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;


  @Setter
  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

}
