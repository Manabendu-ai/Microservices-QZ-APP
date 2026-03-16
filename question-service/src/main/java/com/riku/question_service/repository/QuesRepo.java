package com.riku.question_service.repository;

import com.riku.question_service.model.Question;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuesRepo extends MongoRepository<Question, String> {

    @Aggregation(
            pipeline = {
                    "{$sample: {size :  ?0}}"
            }
    )
    List<Question> findRandomQuestions(int n);

    @Query(value = "{'questionId' :  ?0}", fields = "{'answer' :  1}")
    AnswerOnly findAnswerByQuestionId(int id);
}

