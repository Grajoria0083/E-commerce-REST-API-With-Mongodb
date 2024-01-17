package com.ecommerce.DTO;

import com.ecommerce.model.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private Integer userId;
    private String first_name;
    private String last_name;
    private String email;
    private String mobile;
    private String password;
    private String address_1;
    private String address_2;
    private String city;
    private String state;
    private String country;

}
