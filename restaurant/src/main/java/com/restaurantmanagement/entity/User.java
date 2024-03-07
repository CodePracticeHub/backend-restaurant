//package com.restaurantmanagement.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.io.Serial;
//import java.io.Serializable;
//import java.sql.Timestamp;
//import java.util.Date;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "user", schema = "restaurantdb")
//public class User implements Serializable {
//
//    @Serial
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id")
//    private Long userId;
//
//    @Column(name= "first_name")
//    private String firstName;
//
//    @Column(name= "last_name")
//    private String lastName;
//
//    @Column(name= "email", unique = true)
//    private String email;
//
//    @Column(name = "password")
//    @JsonIgnore
//    private String password;
//
//    @Column(name = "registration_date")
//    private Date registrationDate;
//
//    @Column(name = "created_at", nullable = false, updatable = false)
//    @CreationTimestamp
//    private Timestamp createdAt;
//
//    @Column(name = "updated_at")
//    @UpdateTimestamp
//    private Timestamp updatedAt;
//
//}
