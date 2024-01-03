package com.ecommerce.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginModal {

    @NotNull
    @UniqueElements
    private String username;

    @NotNull
    private String password;
}
