package com.ecommerce.service;

import com.ecommerce.DTO.LoginModal;
import com.ecommerce.model.User;
import org.springframework.security.core.Authentication;

public interface LoginService {

    User login(LoginModal loginModal);
}
