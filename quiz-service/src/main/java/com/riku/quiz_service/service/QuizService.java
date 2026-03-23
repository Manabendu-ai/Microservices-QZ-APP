package com.riku.quiz_service.service;

import com.riku.quiz_service.feign.QuizInterface;
import com.riku.quiz_service.model.Question;
import com.riku.quiz_service.model.QuestionWrapper;
import com.riku.quiz_service.model.Quiz;
import com.riku.quiz_service.model.Responses;
import com.riku.quiz_service.repository.QuizRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepo quizRepo;

    private final QuizInterface quizInterface;

    public ResponseEntity<?> createQuiz(int numQ, String title) {

        List<Integer> questionIds = quizInterface.generateQuestionsForQuiz(numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setQuestionIds(questionIds);
        quiz.setName(title);
        return  ResponseEntity.ok(quizRepo.save(quiz));

    }

    public ResponseEntity<?> getQuizById(String id) {

        try{
            Quiz quiz = quizRepo.findById(id).orElseThrow(
                    ()->new QuizNotFoundException(id)
            );
            return quizInterface.getQuestionsByQuestionId(quiz.getQuestionIds());
        } catch (QuizNotFoundException e){
            return new ResponseEntity<>(new Quiz(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> validateResult(String id, List<Responses> responses) {
        try{
            Quiz quiz = quizRepo.findById(id).orElseThrow(
                    ()->new QuizNotFoundException(id)
            );
            return quizInterface.getScore(responses);
        } catch (QuizNotFoundException e){
            return new ResponseEntity<>(new Quiz(), HttpStatus.BAD_REQUEST);
        }
    }
}


class QuizNotFoundException extends Exception{
    public QuizNotFoundException(String id){
        super("Invalid Quiz with id: "+id);
    }
}