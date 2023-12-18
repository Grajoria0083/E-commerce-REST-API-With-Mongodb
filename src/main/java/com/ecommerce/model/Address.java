package com.ecommerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    private Integer id;

    private String address_1;

    private String address_2;

    private Integer city_id;

    private Integer state_id;

    private Integer country_id;

    private Integer user_id;


}
