package com.ecommerce.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



//@Service
public class CustomerUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<com.ecommerce.model.User> opt= userRepository.findByEmail(username);

        if(opt.isPresent()) {

            com.ecommerce.model.User customer= opt.get();

            List<GrantedAuthority> authorities= new ArrayList<>();

            return new User(customer.getEmail(), customer.getPassword(), authorities);

        }else
            throw new BadCredentialsException("User Details not found with this username: "+username);
    }
}