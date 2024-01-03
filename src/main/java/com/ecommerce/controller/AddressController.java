package com.ecommerce.controller;

import com.ecommerce.Exception.AddressException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Address;
import com.ecommerce.model.CustomSequences;
import com.ecommerce.model.User;
import com.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;


    @PostMapping("/save/{userId}")
    ResponseEntity<User> saveAddress(@RequestBody Address address) throws AddressException, UserException {
        return new ResponseEntity<>(addressService.addAddressToUSer(address), HttpStatus.CREATED);
    }


    @PutMapping("/update")
    ResponseEntity<User> updateAddress(@RequestBody Address address) throws AddressException, UserException {
        return new ResponseEntity<>(addressService.addAddressToUSer(address), HttpStatus.ACCEPTED);
    }

    @GetMapping("/")
    ResponseEntity<List<Address>> getAddresses() throws UserException, AddressException {
        return new ResponseEntity<>(addressService.getAddresses(), HttpStatus.OK);
    }
}
