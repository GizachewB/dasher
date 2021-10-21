package com.example.dasher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * a DTO called when a driver changes the status to delivered
 */
public class DriverInfoToPayment {
    private String orderId;
//    private String restaurantName;
//    private String restaurantEmail;
    private Driver driver;
}
