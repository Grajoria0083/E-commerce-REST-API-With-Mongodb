package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "userDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {


    @Id
    private Integer id;

    private String company;

    private String age;

    private String maritalStatus;

    private Integer userId;

}
