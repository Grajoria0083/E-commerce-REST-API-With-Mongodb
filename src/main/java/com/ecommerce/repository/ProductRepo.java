package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface ProductRepo extends MongoRepository<Product, Integer>, QueryByExampleExecutor<Product> {
    
}
