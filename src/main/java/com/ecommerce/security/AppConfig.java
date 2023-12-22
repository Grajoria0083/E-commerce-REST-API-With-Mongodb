package com.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
//@EnableMethodSecurity
public class AppConfig {


    private String[] publicEndPoint = {"/user/save","/user/update","/user/get/**",
             "/product/view","/cart","/swagger-ui/index.html","/v3/api-docs"};

    private String[] privateEndPoint = {"/user/delete","/user/get/**",
            "/product/**","/product/update"};


    @Bean
    public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {



            http
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/swagger-ui/index.html","/v3/api-docs").hasRole("ADMIN")
                    .requestMatchers(publicEndPoint).permitAll()
//                    .requestMatchers(HttpMethod.POST,"/product/add").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.DELETE,"/product/**").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.GET, "/customer").hasAnyRole("ADMIN","USER")
                    .anyRequest().permitAll().and()
                    .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                    .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
                    .formLogin()
                    .and()
                    .httpBasic();

            return http.build();

        }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

}
