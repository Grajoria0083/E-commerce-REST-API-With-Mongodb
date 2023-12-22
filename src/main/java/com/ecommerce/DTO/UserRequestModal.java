package com.ecommerce.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestModal {

    private int userId;
    private String password;
}
