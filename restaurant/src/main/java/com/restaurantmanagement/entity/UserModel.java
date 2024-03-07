//package com.restaurantmanagement.entity;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class UserModel {
//    /* This @UserModel class will be used to save the password to the database
//    * since @JsonIgnore is being used in the entity. If we do not have this class
//    * the password would not be sent to the database this is why this class was used
//    * in the UserController and mapped to the User entity class*/
//
//    @NotBlank(message = "Name must be provided")
//    private String firstName;
//
//    @NotBlank(message = "Last name must be provided")
//    private String lastName;
//
//    @NotNull(message = "Name must be provided")
//    @Email(message = "Enter a valid email")
//    private String email;
//
//    @NotNull(message = "Name must be provided")
//    @Size(min = 5, message = "Password must be at least 5 characters")
//    private String password;
//
//    private Date registrationDate;
//}
