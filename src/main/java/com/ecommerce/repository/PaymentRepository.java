package com.ecommerce.repository;

import com.ecommerce.model.Payment;
import com.ecommerce.model.Wallet;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentRepository extends MongoRepository<Payment, Integer> {

    Optional<Payment> findByOrderId(@Param("orderId") Integer orderId);
    List<Payment> findByType(@Param("type") String type);
    List<Payment> findByStatus(@Param("status") String status);






}
