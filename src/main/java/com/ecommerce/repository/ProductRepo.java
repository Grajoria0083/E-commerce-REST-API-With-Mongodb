package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;


import java.util.List;

public interface ProductRepo extends MongoRepository<Product, Integer>, QueryByExampleExecutor<Product> {

    List<Product> findByName(String name);
    List<Product> findByCategory(String category);
    List<Product> findBySize(String size);

    @Query("{'category':?0,'size':?1}")
    public List<Product> findByCategoryAndSize(String category, String size);
}
