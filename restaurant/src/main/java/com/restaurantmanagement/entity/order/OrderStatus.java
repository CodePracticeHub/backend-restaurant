package com.restaurantmanagement.entity.order;

import com.restaurantmanagement.entity.order.EOrderStatus;
import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    private Timestamp timestamp;

    public EOrderStatus getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
