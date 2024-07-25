package com.restaurantmanagement.entity.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long orderID;
    private Long orderDateTime;
    private Double totalAmount;
    private Long createdAt;
    private Long updatedAt;
    private String status;
    private String paymentStatus;
    private String deliveryAddress;
    private Long confirmedAt;
    private Long canceledAt;
    private List<OrderItemDTO> orderItems;
    private List<OrderStatusDTO> orderStatuses;
}
