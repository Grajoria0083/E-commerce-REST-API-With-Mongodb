package com.ecommerce.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {

    private Integer id;

    private String name;

    private String description;

    private String category;

    private MultipartFile multipartFile;

    private Integer price;

    private String size;

    public ProductForm(Integer id, String name, String description, String category, Integer price, String size) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.size = size;
    }
}
