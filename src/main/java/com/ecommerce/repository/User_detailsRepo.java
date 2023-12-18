package com.ecommerce.repository;

import com.ecommerce.model.User_details;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface User_detailsRepo extends MongoRepository<User_details, Integer> {
}
