package com.ecommerce.repository;

import com.ecommerce.model.CartCheckout;
import com.ecommerce.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartCheckoutRepository extends MongoRepository<CartCheckout, Integer> {

    Optional<CartCheckout> findByUserId(Integer userId);
}
