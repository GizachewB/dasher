package com.example.dasher.entity;
import com.example.dasher.dto.Driver;
import com.example.dasher.dto.FoodFromRestaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class OrderFromRestaurant {
    public enum Status{
        READY,SHIPPED,DELIVERED
    }
    @Id
    private String id;
    private String rOrderId;
    private long orderId;
    private String userName;
    private List<FoodFromRestaurant> foods;
    private String restaurantName;
    private Driver driver;
    private Status status;
}
