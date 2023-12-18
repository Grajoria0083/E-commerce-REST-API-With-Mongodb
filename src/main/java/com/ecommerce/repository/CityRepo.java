package com.ecommerce.repository;

import com.ecommerce.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepo extends MongoRepository<City, Integer> {
}
