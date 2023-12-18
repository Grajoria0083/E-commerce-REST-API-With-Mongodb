package com.ecommerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "cart_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User_cart {

    @Id
    private Integer id;
    private Integer userId;
    private List<Cart> cartList;
    private Integer orderId;
}
