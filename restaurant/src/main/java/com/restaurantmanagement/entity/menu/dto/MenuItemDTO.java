package com.restaurantmanagement.entity.menu.dto;

import lombok.Data;

@Data
public class MenuItemDTO {
    private Long menuId;
    private String name;
    private String description;
    private double price;
    private String category;
    private String imageURL;
    private Long date;
    private Long createdAt;
    private Long updatedAt;
}
