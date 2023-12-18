package com.ecommerce.controller;


import com.ecommerce.Exception.UserException;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import com.ecommerce.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    MongoOperations mongo;


    @GetMapping("")
    ResponseEntity<List<User>> getUsers() throws UserException {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.ACCEPTED);
    }


    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable int id) throws UserException {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }


    @PostMapping("")
    ResponseEntity<User> saveUser(@RequestBody User user) throws UserException {
        User getUser = userService.addUser(user);
        return new ResponseEntity<>(getUser, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{id}")
    ResponseEntity<User> deleteUserById(@PathVariable int id) throws UserException {
        User getUser = userService.deleteUser(id);
        return new ResponseEntity<>(getUser, HttpStatus.ACCEPTED);
    }


    @PutMapping("")
    ResponseEntity<User> updateUserById(@RequestBody User user) throws UserException {
            User user1 = userService.updateUser(user);
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.ACCEPTED);
    }


    @GetMapping("/details/{id}")
    ResponseEntity<User_details> getUser_detailsDById(@PathVariable int id) throws UserException {
        User_details user = userService.getUser_detailsById(id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @PostMapping("/details")
    ResponseEntity<User_details> saveUser_details(@RequestBody User_details user) throws UserException {
        User_details getUser = userService.addUser_details(user);
        return new ResponseEntity<>(getUser, HttpStatus.ACCEPTED);
    }


    @PutMapping("/details")
    ResponseEntity<User_details> updateUser_detailsById(@RequestBody User_details user) throws UserException {
        User_details user1 = userService.updateUser_details(user);
        return new ResponseEntity<>(user1, HttpStatus.ACCEPTED);
    }






//    =======================================================

    @PostMapping("/wallet")
    ResponseEntity<Wallet> saveUser(@RequestBody Wallet wallet) throws UserException {
        Wallet wallet1 = userService.createWallet(wallet);
        return new ResponseEntity<>(wallet1, HttpStatus.ACCEPTED);
    }

    @GetMapping("/wallet")
    ResponseEntity<String> saveUser(UserRequestModal urm) throws UserException {
        String balance = userService.Wallet(urm);
        return new ResponseEntity<>(balance, HttpStatus.ACCEPTED);
    }

}




