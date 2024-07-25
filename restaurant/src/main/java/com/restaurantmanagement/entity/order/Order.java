package com.restaurantmanagement.entity.order;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import com.restaurantmanagement.security.model.User;

@Entity
@Data
@ToString(exclude = {"orderItems", "user"})
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
    private Double totalAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name="reservation_status")
    private EOrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_payment_status")
    private EOrderPaymentStatus paymentStatus;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private EOrderStatus orderStatus;

    @Column(name = "confirmed_at")
    private Timestamp confirmedAt;

    @Column(name = "canceled_at")
    private Timestamp canceledAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_status_roles",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id"))
    private Set<OrderStatus> orderStatuses = new HashSet<>();

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", orderDateTime=" + orderDateTime +
                ", totalAmount=" + totalAmount +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status='" + status + '\'' +
                ", user=" + (user != null ? user.getId() : null) +
                '}';
    }
}
