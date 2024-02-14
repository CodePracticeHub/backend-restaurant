<<<<<<< HEAD
//package com.restaurantmanagement.entity;
//
//
//public class Reservation {
//
//
//    // Other fields, getters, setters, and methods
//}
=======
package com.restaurantmanagement.entity;

<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.io.Serial;
import java.io.Serializable;
>>>>>>> main
=======
import java.io.Serial;
import java.io.Serializable;
>>>>>>> main
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="reservations")
<<<<<<< HEAD
<<<<<<< HEAD
public class Reservation {
=======
=======
>>>>>>> main
public class Reservation implements Serializable{
	
	@Serial
	private static final long serialVersionUID = 1L;
<<<<<<< HEAD
>>>>>>> main
=======
>>>>>>> main
	
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
	
<<<<<<< HEAD
<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer; 
    
=======
>>>>>>> main
=======
>>>>>>> main
    public enum ReservationStatus {
    	PENDING, CONFIRMED, CANCELED
    }
    
}


<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> reservation
>>>>>>> main
=======
>>>>>>> reservation
>>>>>>> main
