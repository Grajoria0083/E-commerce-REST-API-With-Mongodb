package com.ecommerce.repository;

import com.ecommerce.model.Payment_type;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Payment_typeRepo extends MongoRepository<Payment_type, Integer> {
}
