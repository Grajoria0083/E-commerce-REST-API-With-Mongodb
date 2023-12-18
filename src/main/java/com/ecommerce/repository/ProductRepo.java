package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, Integer> {
}
