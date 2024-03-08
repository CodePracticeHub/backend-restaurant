package com.restaurantmanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="seating")
public class Seating {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seating_id")
    private Long seatingId;
	
	@Column(name = "capacity")
    @NotNull(message = "Capacity cannot be null")
	private int capacity;
	
	@Column(name = "position_description")
    @Size(min = 50, max = 250, message = "Position description must be between 50 and 250 characters")
    private String positionDescription;
	
	@Column(name = "is_available")
    private Boolean isAvailable;

	@Column(name="last_reserved_time")
    private LocalDateTime lastReservedTime;
	
	@OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

}
