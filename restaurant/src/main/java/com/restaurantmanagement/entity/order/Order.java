package com.restaurantmanagement.entity.order;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import lombok.Data;
import com.restaurantmanagement.security.model.User;

@Entity
@Data
@Table(name="customerorder")
public class Order implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name="reservation_status")
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_payment_status")
    private OrderPaymentStatus paymentStatus;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "confirmed_at")
    private Timestamp confirmedAt;

    @Column(name = "canceled_at")
    private Timestamp canceledAt;

    public enum OrderStatus {
        PENDING, CONFIRMED, CANCELED
    }
}
