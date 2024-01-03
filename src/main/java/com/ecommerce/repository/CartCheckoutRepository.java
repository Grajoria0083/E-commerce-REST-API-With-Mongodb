package com.ecommerce.repository;

import com.ecommerce.model.CartCheckout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartCheckoutRepository extends MongoRepository<CartCheckout, Integer> {

    public Optional<CartCheckout> findByUserId(@Param("userId") Integer userId);
}
