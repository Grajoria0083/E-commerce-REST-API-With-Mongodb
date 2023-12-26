package com.ecommerce.controller;


import com.ecommerce.Exception.*;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.ProductService;
import com.ecommerce.serviceImpl.OrderServiceImpl;
import com.ecommerce.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    UserServiceImpl userService;

    @Autowired
    ProductService productService;


    @Autowired
    OrderServiceImpl orderService;

//    @GetMapping("/admin")
//    ResponseEntity<String> getAdmin() throws UserException {
//        return new ResponseEntity<>("only admin can access", HttpStatus.ACCEPTED);
//    }

//    @GetMapping("/customer")
//    ResponseEntity<String> getUser() throws UserException {
//        return new ResponseEntity<>("only customer can access", HttpStatus.ACCEPTED);
//    }


    @GetMapping("/user/view")
    ResponseEntity<List<User>> getUsers() throws UserException {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.ACCEPTED);
    }


    @GetMapping("/user/{id}")
    ResponseEntity<User> getUserById(@PathVariable int id) throws UserException {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/user/delete/{id}")
    ResponseEntity<User> deleteUserById(@PathVariable int id) throws UserException {
        User getUser = userService.deleteUser(id);
        return new ResponseEntity<>(getUser, HttpStatus.ACCEPTED);
    }


    @GetMapping("/product/get/{prodId}")
    ResponseEntity<Product> getProductById(@PathVariable Integer prodId) throws UserException, ProductException {
        return new ResponseEntity<>(productService.getProductById(prodId) , HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/view")
    ResponseEntity<List<Product>> getProducts() throws UserException, ProductException {
        return new ResponseEntity<>(productService.viewAllproducts(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/product/add")
    ResponseEntity<Product> addProduct(@RequestBody Product product) throws UserException, ProductException {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.ACCEPTED);
    }

    @PutMapping("/product/update")
    ResponseEntity<Product> updateProduct(@RequestBody Product product) throws UserException, ProductException {
        return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/product/{prodId}")
    ResponseEntity<String> deleteProductById(@PathVariable Integer prodId) throws UserException, ProductException {
        return new ResponseEntity<>(productService.deleteProductById(prodId) , HttpStatus.ACCEPTED);
    }

    @GetMapping("/order/{orderId}")
    ResponseEntity<Order> getOrderById(@PathVariable Integer orderId) throws OrderException {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }



}
