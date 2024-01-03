package com.ecommerce.repository;

import com.ecommerce.model.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface User_detailsRepo extends MongoRepository<UserDetails, Integer> {

    Optional<UserDetails> findByUserId(@Param("userId") Integer userId);
}

