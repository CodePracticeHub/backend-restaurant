package com.restaurantmanagement.entity.reservation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.restaurantmanagement.security.model.User;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Entity
@Data
@Table(name="reservations")
public class Reservation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reservation_id")
	private Long reservationId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;

	@Column(name="customer_name")
	private String customerName;

	@Column(name="customer_email")
	private String customerEmail;

	@Column(name="customer_phone")
	private String customerPhone;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@Column(name="reservation_date_time")
	private LocalDateTime reservationDateTime;

	@Column(name="number_of_people")
	private int numberOfPeople;

	@Column(name="table_number")
	private int tableNumber;

	@Column(name="reservation_status")
	private ReservationStatus status;

	@Column(name="special_request")
	private String specialRequest;

	public enum ReservationStatus {
		PENDING, CONFIRMED, CANCELED
	}
}
