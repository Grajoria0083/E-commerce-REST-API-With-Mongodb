package com.ecommerce.repository;

import com.ecommerce.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartRepo extends MongoRepository<Cart, Integer> {


    Optional<Cart>  findByUserId(@Param("userId") Integer userId);
}
