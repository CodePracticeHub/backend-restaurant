package com.restaurantmanagement.entity.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_payment_status")
@Getter
@Setter
public class OrderPaymentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EOrderPaymentStatus status;

    public OrderPaymentStatus() {
    }

    public OrderPaymentStatus(EOrderPaymentStatus status) {
        this.status = status;
    }
}
