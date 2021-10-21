package com.example.dasher.controller;

import com.example.dasher.dto.DriverDTO;
import com.example.dasher.dto.OrderStatus;
import com.example.dasher.entity.Driver;
import com.example.dasher.entity.OrderFromRestaurant;
import com.example.dasher.repository.DriverRepository;
import com.example.dasher.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {


    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepository driverRepository;

    @PostMapping("/profile")
    public Driver createProfile(@RequestBody Driver driver){
        return driverService.createProfile(driver);
    }

    @GetMapping("/{driverId}")
    public Driver getDriver(@PathVariable("driverId") long driverId ){
        return driverService.findDriverById(driverId);
    }

    @PatchMapping("/{driverId}")
    public ResponseEntity<?> changeStatus(@PathVariable("driverId")long driverId, @RequestBody DriverDTO driverDTO){
        Driver driver = driverRepository.findById(driverId).get();

        if(driverDTO.getStatus().equals(Driver.Status.OFF) && (driver.getStatus() == Driver.Status.OFF) ){
                return ResponseEntity.badRequest().body("Status is already off");

        }else if(driverDTO.getStatus().equals(Driver.Status.ON)&&(driver.getStatus() == Driver.Status.ON)){
            return ResponseEntity.badRequest().body("Status is already ON");
        } else{

            return ResponseEntity.ok(driverService.changeStatus(driver, driverDTO.getStatus()));
        }
    }
    @GetMapping("/orders")
    public ResponseEntity<?> displayActiveOrders(){
        List<OrderFromRestaurant> orderFromRestaurants = driverService.getAllActiveOrders();
        return ResponseEntity.ok(orderFromRestaurants);
    }

    @PatchMapping("/orders/{orderId}/accept")
    public ResponseEntity<?>acceptOrder(@PathVariable("orderId") String orderId,
                                        @RequestBody OrderStatus status){
        OrderFromRestaurant orderFromRestaurant = driverService.acceptOrder(orderId,status.getStatus(), status.getDriver());

        return ResponseEntity.ok(orderFromRestaurant);

    }
}
