package com.ecommerce.repository;

import com.ecommerce.model.CartDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailsRepo extends MongoRepository<CartDetails, Integer> {



}
