package com.ecommerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    private Integer id;

    private String address_1;

    private String address_2;

    private String city;

    private String state;

    private String country;

    private Integer user_id;

}
