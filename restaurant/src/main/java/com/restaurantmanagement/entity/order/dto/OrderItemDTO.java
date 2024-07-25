package com.restaurantmanagement.entity.order.dto;

import com.restaurantmanagement.entity.menu.dto.MenuItemDTO;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private MenuItemDTO menuItem;
    private int quantity;
    private double price;
}
