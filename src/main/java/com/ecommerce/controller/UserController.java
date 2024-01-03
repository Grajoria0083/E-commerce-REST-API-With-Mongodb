package com.ecommerce.controller;


import com.ecommerce.DTO.UserRequestModal;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    UserService userService;

    @Autowired
    MongoOperations mongo;


    @GetMapping("/view")
    ResponseEntity<List<User>> getUsers() throws UserException {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }


    @GetMapping("/get/{id}")
    ResponseEntity<User> getUserById(@PathVariable int id) throws UserException {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/save")
    ResponseEntity<User> saveUser(@Valid @RequestBody User user) throws UserException {
        User getUser = userService.addUser(user);
        return new ResponseEntity<>(getUser, HttpStatus.CREATED);
    }


    @DeleteMapping("delete/{id}")
    ResponseEntity<User> deleteUserById(@PathVariable int id) throws UserException {
        User getUser = userService.deleteUser(id);
        return new ResponseEntity<>(getUser, HttpStatus.OK);
    }


    @PutMapping("/update")
    ResponseEntity<User> updateUserById(@RequestBody User user) throws UserException {
            User user1 = userService.updateUser(user);
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.ACCEPTED);
    }


    @GetMapping("/details/{id}")
    ResponseEntity<UserDetails> getUser_detailsDById(@PathVariable int id) throws UserException {
        UserDetails user = userService.getUser_detailsById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/details")
    ResponseEntity<UserDetails> saveUser_details(@RequestBody UserDetails user) throws UserException {
        UserDetails getUser = userService.addUser_details(user);
        return new ResponseEntity<>(getUser, HttpStatus.CREATED);
    }


    @PutMapping("/details")
    ResponseEntity<UserDetails> updateUser_detailsById(@RequestBody UserDetails user) throws UserException {
        UserDetails user1 = userService.updateUser_details(user);
        return new ResponseEntity<>(user1, HttpStatus.ACCEPTED);
    }



    @PostMapping("/wallet")
    ResponseEntity<Wallet> addWalletToUser(@RequestBody Wallet wallet) throws UserException {
        Wallet wallet1 = userService.createWallet(wallet);
        return new ResponseEntity<>(wallet1, HttpStatus.CREATED);
    }

    @GetMapping("/wallet")
    ResponseEntity<String> checkBalance(@RequestBody UserRequestModal urm) throws UserException {
        String balance = userService.checkBalance(urm);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }



    @PatchMapping("")
    ResponseEntity<String> updateUserPassword(@RequestBody UserRequestModal userRequestModal) throws UserException {
        return new ResponseEntity<>(userService.updateUserPassword(userRequestModal), HttpStatus.ACCEPTED);
    }

}




