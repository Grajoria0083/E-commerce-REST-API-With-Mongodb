package com.ecommerce.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestModal {

    private Integer orderId;
    private String orderStatus;
}

