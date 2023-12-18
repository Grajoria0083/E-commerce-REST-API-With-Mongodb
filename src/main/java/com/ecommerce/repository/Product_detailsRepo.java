package com.ecommerce.repository;

import com.ecommerce.model.Product_details;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface Product_detailsRepo extends MongoRepository<Product_details, Integer> {

    Optional<Product_details> findByProductId(Integer productId);
}
