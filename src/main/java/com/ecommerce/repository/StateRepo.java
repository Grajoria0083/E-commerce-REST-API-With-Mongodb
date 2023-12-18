package com.ecommerce.repository;

import com.ecommerce.model.State;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StateRepo extends MongoRepository<State, Integer> {
}
