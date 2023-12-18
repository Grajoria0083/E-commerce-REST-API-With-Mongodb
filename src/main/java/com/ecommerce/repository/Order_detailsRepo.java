package com.ecommerce.repository;

import com.ecommerce.model.Order_details;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Order_detailsRepo extends MongoRepository<Order_details, Integer> {
}
