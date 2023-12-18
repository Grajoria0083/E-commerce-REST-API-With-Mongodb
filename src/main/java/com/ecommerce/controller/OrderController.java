package com.ecommerce.controller;


import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.WaletException;
import com.ecommerce.model.Order;
import com.ecommerce.model.Order_details;
import com.ecommerce.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;


    @PostMapping("/{userId}")
    ResponseEntity<Order> order(@RequestBody Order_details orderDetails, @PathVariable Integer userId) throws CartException, WaletException {
        Order order = orderService.order(orderDetails, userId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }


    @GetMapping("/{orderId}")
    ResponseEntity<Order> getOrderById(@PathVariable Integer orderId) throws OrderException {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }




}
