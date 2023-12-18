package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "cartDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User_details {


    @Id
    private Integer id;

    private String company;

    private String age;

    private String marital_status;

    private Integer user_id;

}
