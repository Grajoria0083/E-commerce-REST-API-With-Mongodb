package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Integer id;

    @NotBlank(message = "first_name should not be blank")
    @NotNull(message = "first_name should not be null")
    private String first_name;

    @NotBlank(message = "last_name should not be blank")
    @NotNull(message = "first_name should not be null")
    private String last_name;

    @Email(message = "email should not be email")
    @NotNull(message = "email should not be null")
    @NotEmpty(message = "email should not be empty")
    private String email;

    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    @NotNull(message = "Mobile number should not be null")
    @NotEmpty(message = "Mobile number should not be empty")
    private String mobile;

    @NotBlank(message = "password should not be null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    private List<Address> addresss;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String role;


}
