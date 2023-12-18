package com.ecommerce.repository;

import com.ecommerce.model.User_cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserCartRepository extends MongoRepository<User_cart, Integer> {

    public Optional<User_cart> findByUserId(Integer userId);
//    public Optional<User_cart> findByCartId(Integer cartId);

}
