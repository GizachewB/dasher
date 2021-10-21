package com.example.dasher.service;

import com.example.dasher.dto.OrderStatus;
import com.example.dasher.entity.Driver;
import com.example.dasher.entity.OrderFromRestaurant;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DriverService {
    Driver createProfile(Driver driver);
    Driver changeStatus(Driver driver,Driver.Status status);

    Driver findDriverById(long driverId);

    List<OrderFromRestaurant> getAllActiveOrders();

    OrderFromRestaurant acceptOrder(String orderId, OrderFromRestaurant.Status status, com.example.dasher.dto.Driver driver);
//    Order getAvailablePickups();
}
