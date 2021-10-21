package com.example.dasher.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


//@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    public enum Status {
        ON, OFF
    }

//    @Id
    private long driverId;
    private String firstName;
    private String lastName;
    private Address address;
    private Status status;

}
