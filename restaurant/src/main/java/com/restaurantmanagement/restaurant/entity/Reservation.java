package com.restaurantmanagement.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.sql.Timestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservation" , schema = "restaurantdb")
public class Reservation implements Serializable {

    @Serial
    private static final long serialVersionUID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservationID;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<Order> orders;

    @Column(name = "table_id")
    private int tableID;

    @Column(name = "reservation_date_time")
    private Date reservationDateTime;

    @Column(name = "party_size")
    private int partySize;

    @Column(name = "status")
    private String status;

    @Column(name = "special_requests")
    private String specialRequests;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
}