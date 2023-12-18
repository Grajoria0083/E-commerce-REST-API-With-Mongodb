package com.ecommerce.repository;

import com.ecommerce.model.Order;
import com.ecommerce.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepo extends MongoRepository<Order, Integer> {

    Optional<Order> findByUserId(Integer userId);
}
