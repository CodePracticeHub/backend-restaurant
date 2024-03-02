package com.restaurantmanagement.entity;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.restaurantmanagement.entity.Reservation.ReservationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="customerorder")
public class CustomerOrder implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long orderID;
	

    @Column(name = "order_date_time")
    private Date orderDateTime;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

	@Column(name="reservation_status")
	private OrderStatus status;
	
    public enum OrderStatus {
    	PENDING, CONFIRMED, CANCELED
    }
	
}
