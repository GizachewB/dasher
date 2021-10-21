package com.example.dasher.repository;

import com.example.dasher.entity.Driver;
import com.example.dasher.entity.OrderFromRestaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<OrderFromRestaurant,String> {

//    List<OrderFromRestaurant> findOrderFromRestaurantsByStatusReady();
    List<OrderFromRestaurant> findByStatus(String status);
//    List<OrderFromRestaurant> findAllByStatusReady(OrderFromRestaurant.Status status);
}
