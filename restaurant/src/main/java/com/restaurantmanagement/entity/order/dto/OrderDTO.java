package com.restaurantmanagement.entity.order.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.restaurantmanagement.entity.order.*;
import com.restaurantmanagement.entity.user.dto.UserDTO;
import lombok.Data;

@Data
public class OrderDTO {
    private Long orderID;
    private Date orderDateTime;
    private Double totalAmount;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private EOrderStatus status;
    private UserDTO user;
    private List<OrderItem> orderItems;
    private EOrderPaymentStatus paymentStatus;
    private String deliveryAddress;
    private EOrderStatus orderStatus;
    private Timestamp confirmedAt;
    private Timestamp canceledAt;
    private Set<OrderStatus> orderStatuses;

    public OrderDTO(Order order) {
        this.orderID = order.getOrderID();
        this.orderDateTime = order.getOrderDateTime();
        this.totalAmount = order.getTotalAmount();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
        this.status = order.getStatus();
        this.user = new UserDTO(order.getUser());
        this.orderItems = order.getOrderItems();
        this.paymentStatus = order.getPaymentStatus();
        this.deliveryAddress = order.getDeliveryAddress();
        this.orderStatus = order.getOrderStatus();
        this.confirmedAt = order.getConfirmedAt();
        this.canceledAt = order.getCanceledAt();
        this.orderStatuses = order.getOrderStatuses();
    }
}
