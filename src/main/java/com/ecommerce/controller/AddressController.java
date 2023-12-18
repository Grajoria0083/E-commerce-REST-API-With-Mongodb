package com.ecommerce.controller;

import com.ecommerce.Exception.AddressException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Address;
import com.ecommerce.model.CustomSequences;
import com.ecommerce.model.User;
import com.ecommerce.serviceImpl.AddressServiceImpl;
import com.ecommerce.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressServiceImpl addressService;

    @Autowired
    UserServiceImpl userService;


    @Autowired
    CustomSequences sequences;


    @PostMapping("/save/{userId}")
    ResponseEntity<User> saveAddress(@RequestBody Address address, @PathVariable Integer userId) throws AddressException, UserException {
        System.out.println("working...");
        address.setId(sequences.getNextSequence("address"));
        return new ResponseEntity<>(addressService.addAddressToUSer(address, userId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/")
    ResponseEntity<List<Address>> getAddresses() throws UserException, AddressException {
        return new ResponseEntity<>(addressService.getAddresses(), HttpStatus.ACCEPTED);
    }
}
