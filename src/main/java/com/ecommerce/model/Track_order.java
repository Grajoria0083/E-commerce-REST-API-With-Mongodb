package com.ecommerce.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Track_order {

    @Id
    private Integer id;

    private String order_status;


    @CreatedDate
    private LocalDate created_at;


    @LastModifiedDate
    private LocalDate updated_at;

    private Integer product_id;


}
