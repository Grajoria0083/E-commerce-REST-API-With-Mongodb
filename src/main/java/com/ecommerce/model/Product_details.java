package com.ecommerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product_details {

    @Id
    private Integer id;

    private Integer available_count;

    private String meta_key;

    private String meta_value;

    private Integer productId;
}
