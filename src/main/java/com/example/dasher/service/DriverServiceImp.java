package com.example.dasher.service;

import com.example.dasher.dto.DriverInfoToPayment;
import com.example.dasher.dto.OrderStatus;
import com.example.dasher.dto.ShippedStatus;
import com.example.dasher.entity.Driver;
import com.example.dasher.entity.OrderFromRestaurant;
import com.example.dasher.repository.DriverRepository;
import com.example.dasher.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImp implements DriverService{

    private final String SHIPPED_TOPIC = "toRestaurantStatusShipped";
    private final String PAYMENT_TOPIC = "payment";

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private OrderRepository orderRepository;

    ObjectMapper objectMapper= new ObjectMapper();

    ModelMapper modelMapper = new ModelMapper();



    @KafkaListener(topics = "toDriverTopic", groupId = "group_id")
    public void consumeFromTopic(String message) {
        OrderFromRestaurant orderFromRestaurant = null;
        try {
            orderFromRestaurant = objectMapper.readValue(message,OrderFromRestaurant.class);
            System.out.println(orderFromRestaurant);
            orderFromRestaurant.setStatus(OrderFromRestaurant.Status.READY);
            orderRepository.save(orderFromRestaurant);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Consumed message " + orderFromRestaurant);
    }


    @Override
    public Driver createProfile(Driver driver) {
        driver.setStatus(Driver.Status.ON);
        return driverRepository.save(driver);
    }

    @Override
    public Driver changeStatus(Driver driver, Driver.Status status) {
        driver.setStatus(status);
        return driverRepository.save(driver);
    }

    @Override
    public Driver findDriverById(long driverId) {
        return driverRepository.findById(driverId).get();
    }

    @Override
    public List<OrderFromRestaurant> getAllActiveOrders() {
        return orderRepository.findByStatus("READY");
    }

    @Override
    public OrderFromRestaurant acceptOrder(String orderId, OrderFromRestaurant.Status status, com.example.dasher.dto.Driver driver) {
        OrderFromRestaurant orderFromRestaurant = orderRepository.findById(orderId).get();

        if(orderFromRestaurant.getStatus().equals(OrderFromRestaurant.Status.READY) &&
                status == OrderFromRestaurant.Status.SHIPPED){

            orderFromRestaurant.setStatus(status);
            orderFromRestaurant.setDriver(driver);
            ShippedStatus shippedStatus = modelMapper.map(orderFromRestaurant, ShippedStatus.class);
            System.out.println(shippedStatus);

            kafkaTemplate.send(SHIPPED_TOPIC,shippedStatus);


        } else if(orderFromRestaurant.getStatus().equals(OrderFromRestaurant.Status.SHIPPED) &&
            status == OrderFromRestaurant.Status.DELIVERED){

            orderFromRestaurant.setStatus(status);
            ShippedStatus shippedStatus = modelMapper.map(orderFromRestaurant, ShippedStatus.class);
            System.out.println(shippedStatus);

            kafkaTemplate.send(SHIPPED_TOPIC,shippedStatus);


            //Todo ------ Send the User Information to the Payment Service

            DriverInfoToPayment driverInfoToPayment = modelMapper.map(orderFromRestaurant,DriverInfoToPayment.class);
            kafkaTemplate.send(PAYMENT_TOPIC,driverInfoToPayment);
            System.out.println(driverInfoToPayment);
        }
        else{
            return null;
        }
        return orderRepository.save(orderFromRestaurant);
    }

}
