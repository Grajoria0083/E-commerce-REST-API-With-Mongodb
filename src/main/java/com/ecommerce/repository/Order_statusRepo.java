package com.ecommerce.repository;

import com.ecommerce.model.Order_status;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Order_statusRepo extends MongoRepository<Order_status, Integer> {



}
