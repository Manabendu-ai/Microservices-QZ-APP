package com.riku.quizz.authservice.repository;

import com.riku.quizz.authservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByEmail(String userId);

    boolean existsByEmail(String id);
}
