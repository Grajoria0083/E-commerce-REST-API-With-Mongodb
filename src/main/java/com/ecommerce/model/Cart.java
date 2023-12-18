package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    private Integer id;

//    private Integer quantity;
//
//    private LocalDateTime created_at;
//
//    private LocalDateTime updated_at;
//
//    private Integer productId;
//
//    private Integer userId;

//============================================
    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private Integer userId;

    private List<CartDetails> cartDetailsList;

}
