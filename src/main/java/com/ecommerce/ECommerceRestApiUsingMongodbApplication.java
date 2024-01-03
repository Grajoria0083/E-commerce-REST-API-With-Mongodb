package com.ecommerce;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.OffsetDateTime;
import java.util.Optional;


@OpenAPIDefinition(info = @Info(
		title = "E-commerce REST API",
		description = "All API definitions",
		version = "2.0.2"))
@SpringBootApplication
@EnableMongoAuditing
public class ECommerceRestApiUsingMongodbApplication{

	public static void main(String[] args) {
		SpringApplication.run(ECommerceRestApiUsingMongodbApplication.class, args);


	}

}
