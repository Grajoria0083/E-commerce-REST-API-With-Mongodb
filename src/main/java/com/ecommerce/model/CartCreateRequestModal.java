package com.ecommerce.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartCreateRequestModal {

    private int userId;
    private int productId;
    private int quantity;

}
