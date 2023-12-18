package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Integer id;

    private String first_name;

    private String last_name;

    private String email;

    private String mobile;

    private String password;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private List<Address> addresss;



}
