package com.ecommerce.repository;

import com.ecommerce.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepo extends MongoRepository<Address, Integer> {
}
