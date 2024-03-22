package com.restaurantmanagement.entity;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="restaurant")
public class Restaurant {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id")
    private Long restaurantId;
	
	@Column(name = "name")
	@NotNull(message = "Name cannot be null")
	@Size(max = 250, message = "Name must be between 50 and 250 characters")
	private String name;
	
	@Column(name = "address")
    @Size(max = 250, message = "Address must be between 50 and 250 characters")
    private String address;
	
	@Column(name = "contact_phone")
    @Size(max = 20, message = "ContactPhone must be between 50 and 250 characters")
    private String contactPhone;

	@Column(name = "opening_hours")
    @Size(max = 5, message = "Opening Hours must be between 50 and 250 characters")
	@Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Opening Time must be in HH:MM format")
    private String openingHours;
	
	
	@Column(name = "closing_hours")
    @Size(max = 5, message = "Closing Hours must be between 50 and 250 characters")
	@Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Closing Time must be in HH:MM format")
    private String closingHours;
	
	@Column(name = "description")
    @Size(max = 250, message = "Description must be between 50 and 250 characters")
    private String description;
	
	@Column(name = "cuisine_type")
    @Size(max = 50, message = "Cuisine Type must be between 50 and 250 characters")
    private String cuisineType;
	
	@Column(name = "is_available")
    private Boolean isAvailable;

}
