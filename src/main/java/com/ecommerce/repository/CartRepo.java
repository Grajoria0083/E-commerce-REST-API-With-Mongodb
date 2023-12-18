package com.ecommerce.repository;

import com.ecommerce.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends MongoRepository<Cart, Integer> {


//    @Query("{'productId':?0,'userId':?1}")
//    public Optional<Cart> findByProductIdAndUserId(Integer productId, Integer userId);

//    List<Cart> findByUserId(Integer userId);

    Optional<Cart>  findByUserId(Integer userId);
}
