package com.ecommerce.serviceImpl;

import com.ecommerce.DTO.LoginModal;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;


    @Override
    public User login(LoginModal loginModal) {

        return userRepository.findByEmail(loginModal.getUsername()).orElseThrow(() -> new BadCredentialsException("Invalid Username or password"));
    }
}
