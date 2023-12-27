package com.ecommerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestModal {

    private String name;

    private String description;

    private String category;

    private String small_thumbnail;

    private String large_thumbnail;

    private Integer price;

    private String size;

    private Integer available_count;

    private String meta_key;

    private String meta_value;


}
