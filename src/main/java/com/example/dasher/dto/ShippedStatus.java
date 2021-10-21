package com.example.dasher.dto;

import com.example.dasher.entity.OrderFromRestaurant;
import lombok.Data;

@Data
public class ShippedStatus {
    private String rOrderId;
    private long orderId;
    private OrderFromRestaurant.Status status;
}
