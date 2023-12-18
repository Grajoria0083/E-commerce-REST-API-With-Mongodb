package com.ecommerce.repository;

import com.ecommerce.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WalletRepository extends MongoRepository<Wallet, Integer> {

    Optional<Wallet> findByUserId(Integer userId);


}


