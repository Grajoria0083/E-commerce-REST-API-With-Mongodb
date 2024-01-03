package com.ecommerce.repository;

import com.ecommerce.model.ProductDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface Product_detailsRepo extends MongoRepository<ProductDetails, Integer> {

    Optional<ProductDetails> findByProductId(@Param("productId") Integer productId);
}
