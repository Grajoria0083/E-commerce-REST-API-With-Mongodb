package com.ecommerce.repository;

import com.ecommerce.model.OrderDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_detailsRepo extends MongoRepository<OrderDetails, Integer> {
}
