package com.ecommerce.controller;


import com.ecommerce.DTO.LoginModal;
import com.ecommerce.model.User;
import com.ecommerce.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class LoginController {


    @Autowired
    LoginService loginService;


    @PostMapping("/signIn")
    public ResponseEntity<User> getLoggedInCustomerHandler(@RequestBody LoginModal loginModal){

        User user= loginService.login(loginModal);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
}
