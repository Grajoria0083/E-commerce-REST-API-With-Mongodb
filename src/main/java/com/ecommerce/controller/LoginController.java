package com.ecommerce.controller;


import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class LoginController {


    @Autowired
    UserRepository userRepository;


    @GetMapping("/signIn")
    public ResponseEntity<User> getLoggedInCustomerDetailsHandler(Authentication auth){

        User user= userRepository.findByEmail(auth.getName()).orElseThrow(() -> new BadCredentialsException("Invalid Username or password"));

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
}
