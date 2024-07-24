package com.restaurantmanagement.entity.order;

import jakarta.persistence.*;

@Entity
@Table(name = "order_statuses")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EOrderStatus name;

    public OrderStatus() {

    }

    public OrderStatus(EOrderStatus name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EOrderStatus getName() {
        return name;
    }

    public void setName(EOrderStatus name) {
        this.name = name;
    }
}
