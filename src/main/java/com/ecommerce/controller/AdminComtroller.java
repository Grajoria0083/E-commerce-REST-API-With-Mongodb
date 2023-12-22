package com.ecommerce.controller;


import com.ecommerce.Exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminComtroller {

    @GetMapping("/admin")
    ResponseEntity<String> getAdmin() throws UserException {
        return new ResponseEntity<>("only admin can access", HttpStatus.ACCEPTED);
    }

    @GetMapping("/customer")
    ResponseEntity<String> getUser() throws UserException {
        return new ResponseEntity<>("only customer can access", HttpStatus.ACCEPTED);
    }
}
