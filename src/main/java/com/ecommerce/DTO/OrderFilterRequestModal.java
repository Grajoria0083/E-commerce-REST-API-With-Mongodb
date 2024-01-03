package com.ecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilterRequestModal {


    private Integer userId;

    private String productName;

    private String paymentStatus;

    private String orderStatus;

    private LocalDate startDate;

    private LocalDate endDate;

}
