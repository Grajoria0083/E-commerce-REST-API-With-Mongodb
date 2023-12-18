package com.ecommerce.repository;

import com.ecommerce.model.CartDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartDetailsRepo extends MongoRepository<CartDetails, Integer> {



}
