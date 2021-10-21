package com.example.dasher.dto;

import com.example.dasher.entity.Driver;
import lombok.Data;

@Data
public class DriverDTO {
    private long driverId;
    private Driver.Status status;
}
