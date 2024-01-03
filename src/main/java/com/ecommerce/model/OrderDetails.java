package com.ecommerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "orderDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

    @Id
    private Integer id;

    private Integer totalAmount;

    private String password;

    private String paymentType;

    private Integer userId;

}
