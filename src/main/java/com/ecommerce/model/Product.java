package com.ecommerce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Document(collection = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    @Id
    private Integer id;

    @NotBlank(message = "Product name is mendetory!")
    @NotNull(message = "Product name is mendetory!")
    private String name;

    private String description;

    @NotBlank(message = "Product category name is mendetory!")
    @NotNull(message = "Product category name is mendetory!")
    private String category;

    @NotBlank(message = "Product small_thumbnail name is mendetory!")
    @NotNull(message = "Product small_thumbnail name is mendetory!")
    private String small_thumbnail;

    private String large_thumbnail;

    @NotBlank(message = "Product price name is mendetory!")
    @NotNull(message = "Product price name is mendetory!")
    private Integer price;

    @NotBlank(message = "Product size name is mendetory!")
    @NotNull(message = "Product size name is mendetory!")
    private String size;

//    private MultipartFile multipartFile;
//
    private String filePath;

    private byte[] file;
    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    public Product(Integer id, String name, String description, String category, Integer price, String size) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.size = size;
    }

    //    public String generateBase64Image() {
//        return Base64.encodeBase64String(this.file);
//    }
}
