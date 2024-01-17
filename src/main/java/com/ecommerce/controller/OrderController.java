package com.ecommerce.controller;


import com.ecommerce.DTO.OrderFilterRequestModal;
import com.ecommerce.DTO.OrderRequestModal;
import com.ecommerce.Exception.CartException;
import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.WalletException;
import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping("/place")
    ResponseEntity<Order> placeOrder(@RequestBody OrderRequestModal orderRequestModal) throws CartException, WalletException {
        Order order = orderService.orderByUser(orderRequestModal);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/")
    ResponseEntity<List<Order>> getAllOrders() throws OrderException {
        List<Order> orders = orderService.getOrders();
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }


    @GetMapping("/getByOrderId/{orderId}")
    ResponseEntity<Order> getOrderById(@PathVariable Integer orderId) throws OrderException {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @DeleteMapping("/{orderId}")
    ResponseEntity<String> deleteOrderById(@PathVariable Integer orderId) throws OrderException {
        String order = orderService.deleteOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }



    @GetMapping("/getByUserId/{userId}")
    ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Integer userId) throws OrderException {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/updateOrderStatus")
    ResponseEntity<String> updateOrderStatusByOrderId(@RequestBody OrderRequestModal orm) throws OrderException {
        String orderStatus = orderService.updateOrderStatusByOrderId(orm);
        return new ResponseEntity<>(orderStatus, HttpStatus.OK);
    }

    @PostMapping("/filter")
    ResponseEntity<List<Order>> getOrdersByUserId(@RequestBody OrderFilterRequestModal orderFilterRequestModal) throws OrderException {
        List<Order> orders = orderService.orderFilter(orderFilterRequestModal);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }




}
