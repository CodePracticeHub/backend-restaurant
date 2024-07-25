package com.restaurantmanagement.entity.order.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderStatusDTO {
    private String status;
    private LocalDateTime timestamp;
}
