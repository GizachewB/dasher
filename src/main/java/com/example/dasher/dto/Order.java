package com.example.dasher.dto;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private User user;
    private List<Food> foodOrders;
    private Restaurant restaurant;
//    private Payment payment;
}