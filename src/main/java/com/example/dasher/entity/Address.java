package com.example.dasher.entity;

import lombok.Data;

@Data
public class Address {
    private String streetAddress;
    private long zipCode;
    private String city;
}
