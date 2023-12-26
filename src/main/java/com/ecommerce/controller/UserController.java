package com.ecommerce.controller;


import com.ecommerce.DTO.UserRequestModal;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import com.ecommerce.serviceImpl.UserServiceImpl;
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
    UserServiceImpl userService;

    @Autowired
    MongoOperations mongo;


    @GetMapping("/view")
    ResponseEntity<List<User>> getUsers() throws UserException {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.ACCEPTED);
    }


    @GetMapping("/get/{id}")
    ResponseEntity<User> getUserById(@PathVariable int id) throws UserException {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }


    @PostMapping("/save")
    ResponseEntity<User> saveUser(@Valid @RequestBody User user) throws UserException {
        User getUser = userService.addUser(user);
        return new ResponseEntity<>(getUser, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("delete/{id}")
    ResponseEntity<User> deleteUserById(@PathVariable int id) throws UserException {
        User getUser = userService.deleteUser(id);
        return new ResponseEntity<>(getUser, HttpStatus.ACCEPTED);
    }


    @PutMapping("/update")
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
    ResponseEntity<Wallet> addWalletToUser(@RequestBody Wallet wallet) throws UserException {
        Wallet wallet1 = userService.createWallet(wallet);
        return new ResponseEntity<>(wallet1, HttpStatus.ACCEPTED);
    }

//    @GetMapping("/wallet")
//    ResponseEntity<String> saveUser(@RequestBody UserRequestModal urm) throws UserException {
//        String balance = userService.Wallet(urm);
//        return new ResponseEntity<>(balance, HttpStatus.ACCEPTED);
//    }



//    @GetMapping("/admin")
//    ResponseEntity<String> getAdmin() throws UserException {
//        return new ResponseEntity<>("only admin can access", HttpStatus.ACCEPTED);
//    }
//
//    @GetMapping("/customer")
//    ResponseEntity<String> getUser() throws UserException {
//        return new ResponseEntity<>("only user can access", HttpStatus.ACCEPTED);
//    }

    @PatchMapping("")
    ResponseEntity<String> updateUserPassword(@RequestBody UserRequestModal userRequestModal) throws UserException {
        return new ResponseEntity<>(userService.updateUserPassword(userRequestModal), HttpStatus.ACCEPTED);
    }

}




