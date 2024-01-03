package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CartDetails {


    private Integer id;

    private Integer quantity;

    private Product product;

}
