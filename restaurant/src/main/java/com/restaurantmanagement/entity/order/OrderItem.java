package com.restaurantmanagement.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import com.restaurantmanagement.entity.menu.Menu;

@Entity
@Data
@Table(name="order_item")
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name="menu_item_id", nullable=false)
    private Menu menuItem;

    @Column(name="quantity")
    private int quantity;

    @Column(name="price")
    private BigDecimal price;
}
