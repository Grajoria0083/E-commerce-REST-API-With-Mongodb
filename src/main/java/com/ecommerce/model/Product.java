package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Document(collection = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    @Id
    private Integer id;
    private String name;
    private String description;
    private String category;
    private String small_thumbnail;
    private String large_thumbnail;
    private Integer price;
    private String size;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
