package com.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "wallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    @Id
    private Integer id;

    private String password;

    private Integer balance;

    private Integer userId;
}
