package com.ecommerce.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestModal {

    private Integer userId;
    private Integer orderId;
    private String orderStatus;
    private String paymentStatus;
    private String paymentType;
    private boolean active;
}

