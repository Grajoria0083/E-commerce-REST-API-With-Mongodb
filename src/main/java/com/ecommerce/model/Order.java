package com.ecommerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id

    private Integer id;

//    private Integer productId;

    private Integer userId;

//    private Integer orderStatusId;
//
//    private Integer paymentStatusId;
//
//    private Integer paymentTypeId;

    @CreatedDate
    private LocalDate created_at;


    @LastModifiedDate
    private LocalDate updated_at;

    private Integer orderDetaildsId;

    private CartCheckout cartCheckout;

//    private List<Product> products;

}
