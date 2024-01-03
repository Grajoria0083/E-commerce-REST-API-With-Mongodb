package com.ecommerce.repository;

import com.ecommerce.model.Order;
import com.ecommerce.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepo extends MongoRepository<Order, Integer> {

    List<Order> findByUserId(@Param("userId") Integer userId);

//    @Query("{ $group: { _id: '$userId', count: { $sum: 1 } } }")
//    List<Order> countOrdersByUserId();

    @Query(value = "{ $group: { _id: '$userId', count: { $sum: 1 } } }", count = true)
    Integer countUniqueOrdersByUserId();
    List<Order> findByPaymentStatus(@Param("paymentStatus") String paymentStatus);

    List<Order> findByOrderStatus(@Param("orderStatus") String orderStatus);


}
