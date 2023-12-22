package com.ecommerce.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductFilterRequestModal {

    private String name;
    private String category;
    private Integer minPrice;
    private Integer maxPrice;
    private String size;

}
