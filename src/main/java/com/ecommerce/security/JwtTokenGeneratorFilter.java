package com.ecommerce.security;

import java.util.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;


@Component
public class JwtTokenGeneratorFilter extends OncePerRequestFilter{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("inside JwtToken Generator..");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication!=null){

            SecretKey secretKey = Keys.hmacShaKeyFor(SecurityContent.JWT_KEY.getBytes());

            String jwt = Jwts.builder()
                    .setIssuer("Gaurav")
                    .setSubject("jwt token")
                    .claim("username",authentication.getName())
                    .claim("role", getRole(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + 1800000))
                    .signWith(secretKey).compact();

            response.setHeader(SecurityContent.JWT_HEADER,jwt);

        }

        filterChain.doFilter(request, response);


    }




    private String getRole(Collection<? extends GrantedAuthority> collection) {

        String role="";
        for(GrantedAuthority ga:collection) {
            role= ga.getAuthority();
        }

        System.out.println("getRole : "+role);
        return role;
    }




    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {

        Set<String> authoritiesSet = new HashSet<>();

        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);

    }



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return !request.getServletPath().equals("/signIn");
    }
}






