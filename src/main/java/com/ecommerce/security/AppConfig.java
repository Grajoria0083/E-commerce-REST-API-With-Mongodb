package com.ecommerce.security;

import com.ecommerce.security.JwtTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
//@EnableMethodSecurity
public class AppConfig {

    @Bean
    public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {


//        return http
//                .csrf(csrf->csrf.disable())
//                .authorizeRequests(auth->auth.requestMatchers(HttpMethod.POST,"/user/").permitAll())
//                .authorizeRequests(auth->auth.anyRequest().authenticated())
//                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .httpBasic(Customizer.withDefaults())
//                .build();


//        http
//                .csrf(csrf->csrf.disable())
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeRequests()
//                .requestMatchers(HttpMethod.POST,"/user").permitAll()
//                .anyRequest().authenticated().and()
//                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class);
//        return http.build();


            http
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers(HttpMethod.POST, "/user").permitAll()
                    .anyRequest().authenticated().and()
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
