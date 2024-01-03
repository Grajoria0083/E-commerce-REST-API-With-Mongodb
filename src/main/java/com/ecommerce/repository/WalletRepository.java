package com.ecommerce.repository;

import com.ecommerce.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WalletRepository extends MongoRepository<Wallet, Integer> {

    Optional<Wallet> findByUserId(Integer userId);


}


