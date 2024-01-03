package com.ecommerce.repository;

import com.ecommerce.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepo extends MongoRepository<Address, Integer> {
}
