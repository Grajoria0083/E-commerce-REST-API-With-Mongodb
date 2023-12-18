package com.ecommerce.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyErrorDetails {

    String msg;
    String details;
    LocalDateTime localDateTime;
}
