package com.ecommerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductD {

    private Integer productId;

    private String name;

    private Integer price;

    private Integer quantity;

}
