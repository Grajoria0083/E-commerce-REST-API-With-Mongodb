package com.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class AppConfig {


    private String[] publicEndPoint = {"/user/save","/user/get/**","/product/filter",
             "/product/view","/cart","/swagger-ui/index.html","/v3/api-docs","/signIn"};

    private String[] privateEndPoint = {"/user/update","/user/delete/**","/user/update","/order"};

    private String[] adminEndPoint = {"/admin/user/view","/admin/user/**","/admin/user/delete/**",
            "/admin/product/get/**","/admin/product/view","/admin/product/add","/admin/product/update",
            "/admin/product/delete/**","/admin/order/**"};


    @Bean
    public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {



            http
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers(publicEndPoint).permitAll()
//                    .requestMatchers(privateEndPoint).hasRole("USER")
//                    .requestMatchers(adminEndPoint).hasRole("ADMIN")
                    .anyRequest().permitAll().and()
//                    .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//                    .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
//                    .formLogin()
//                    .and()
                    .httpBasic();

            return http.build();

        }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

}
