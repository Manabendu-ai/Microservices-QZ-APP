package com.riku.quiz_service.repository;

import com.riku.QuizzAPP.model.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepo extends MongoRepository<Quiz, String> {
}
