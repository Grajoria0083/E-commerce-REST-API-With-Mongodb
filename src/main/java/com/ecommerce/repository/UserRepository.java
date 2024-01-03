package com.ecommerce.repository;

import com.ecommerce.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByEmail(@Param("username") String username);

    List<User> findByRole(@Param("role") String role);
}
