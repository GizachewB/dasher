package com.example.dasher.dto;

import com.example.dasher.entity.OrderFromRestaurant;
import lombok.Data;

@Data
public class OrderStatus {
    private String id;
    private OrderFromRestaurant.Status status;
    private Driver driver;
}
