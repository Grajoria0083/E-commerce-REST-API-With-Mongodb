package com.ecommerce.repository;

import com.ecommerce.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountryRepo extends MongoRepository<Country, Integer> {
}
