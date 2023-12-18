package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class CartDetails {

//    private Integer id;
//
//    private Integer quantity;
//
//    private Integer totalAmount;
//
//    private Integer userId;
//
//    private List<ProductD> productList;


//    ==========================

    private Integer id;

    private Integer quantity;

    private Product product;

}
