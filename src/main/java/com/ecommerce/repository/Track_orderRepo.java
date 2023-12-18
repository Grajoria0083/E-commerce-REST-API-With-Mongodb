package com.ecommerce.repository;

import com.ecommerce.model.Track_order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Track_orderRepo extends MongoRepository<Track_order, Integer> {
}
