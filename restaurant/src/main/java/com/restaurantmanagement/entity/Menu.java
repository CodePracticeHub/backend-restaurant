package com.restaurantmanagement.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Column(name = "description")
    @Size(min = 50, max = 250, message = "Description must be between 50 and 250 characters")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

    @Column(name = "category")
    @NotBlank(message = "Category cannot be empty")
    private String category;

    @Column(name = "image_url")
    private String imageURL;

    @Future(message = "Date must be in the future")
    @NotNull(message = "Date cannot be null")
    private Date date;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;


}
