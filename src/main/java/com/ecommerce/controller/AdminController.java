package com.ecommerce.controller;


import com.ecommerce.DTO.PaymentRequestModal;
import com.ecommerce.DTO.RecordCountDAO;
import com.ecommerce.Exception.*;
import com.ecommerce.model.Order;
import com.ecommerce.model.Payment;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.AdminService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;


    @Autowired
    OrderService orderService;


    @Autowired
    AdminService adminService;


    @GetMapping("/user/view")
    ResponseEntity<List<User>> getUsers() throws UserException {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }


    @GetMapping("/user/{id}")
    ResponseEntity<User> getUserById(@PathVariable int id) throws UserException {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping("/user/delete/{id}")
    ResponseEntity<User> deleteUserById(@PathVariable int id) throws UserException {
        User getUser = userService.deleteUser(id);
        return new ResponseEntity<>(getUser, HttpStatus.OK);
    }


    @GetMapping("/product/get/{prodId}")
    ResponseEntity<Product> getProductById(@PathVariable Integer prodId) throws UserException, ProductException {
        return new ResponseEntity<>(productService.getProductById(prodId) , HttpStatus.OK);
    }

    @GetMapping("/product/view")
    ResponseEntity<List<Product>> getProducts() throws UserException, ProductException {
        return new ResponseEntity<>(productService.viewAllproducts(), HttpStatus.OK);
    }

    @PostMapping("/product/add")
    ResponseEntity<Product> addProduct(@RequestBody Product product) throws UserException, ProductException {
        return new ResponseEntity<>(productService.addProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/product/update")
    ResponseEntity<Product> updateProduct(@RequestBody Product product) throws UserException, ProductException {
        return new ResponseEntity<>(productService.updateProduct(product), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/product/{prodId}")
    ResponseEntity<String> deleteProductById(@PathVariable Integer prodId) throws UserException, ProductException {
        return new ResponseEntity<>(productService.deleteProductById(prodId) , HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    ResponseEntity<Order> getOrderById(@PathVariable Integer orderId) throws OrderException {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @GetMapping("/record/counts")
    ResponseEntity<RecordCountDAO> getOrderById() throws OrderException {
        RecordCountDAO rcd = adminService.viewRecordCount();
        return new ResponseEntity<>(rcd, HttpStatus.OK);
    }


    @PostMapping("/payment/history")
    ResponseEntity<List<Payment>> getPaymentHistory(@RequestBody PaymentRequestModal paymentRequestModal) throws OrderException {
        List<Payment> paymentList = adminService.viewPaymentHistory(paymentRequestModal);
        return new ResponseEntity<>(paymentList, HttpStatus.ACCEPTED);
    }



}
