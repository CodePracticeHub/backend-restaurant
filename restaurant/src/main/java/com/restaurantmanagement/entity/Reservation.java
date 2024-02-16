
package com.restaurantmanagement.entity;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="reservations")
public class Reservation implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reservation_id")
	private Long reservationId;
	
	@Column(name="customer_name")
	private String customerName;
	
	@Column(name="customer_email")
	private String customerEmail;
	
	@Column(name="customer_phone")
	private String customerPhone;
	
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

