package com.example.dasher.repository;

import com.example.dasher.entity.Driver;
import com.example.dasher.entity.OrderFromRestaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends MongoRepository<Driver,Long> {
}
