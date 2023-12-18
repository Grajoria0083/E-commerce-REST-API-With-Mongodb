package com.ecommerce.repository;

import com.ecommerce.model.Payment;
import com.ecommerce.model.Wallet;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, Integer> {

//    Optional<Payment> findByUserId(Integer userId);

    Optional<Payment> findByOrderId(Integer orderId);

//    Optional<Order> findByUserId(Integer userId);



}
