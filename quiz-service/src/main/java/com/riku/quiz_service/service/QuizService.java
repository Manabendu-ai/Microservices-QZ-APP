package com.riku.quiz_service.service;

import com.riku.QuizzAPP.model.Question;
import com.riku.QuizzAPP.model.Quiz;
import com.riku.QuizzAPP.model.Responses;
import com.riku.QuizzAPP.repository.QuesRepo;
import com.riku.QuizzAPP.repository.QuizRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuesRepo quesRepo;
    private final QuestionService quesService;
    private final QuizRepo quizRepo;

    public ResponseEntity<?> createQuiz(int numQ, String title){
        List<Question> questions = quesRepo.findRandomQuestions(numQ);

        Quiz quiz = new Quiz();
        quiz.setName(title);
        quiz.setQuestions(questions);

        return new ResponseEntity<>(quizRepo.save(quiz), HttpStatus.CREATED);

    }

    public ResponseEntity<?> getQuizById(String id) {
        try{
            Quiz quiz = quizRepo.findById(id).orElseThrow(
                    ()->new QuizNotFoundException(id)
            );
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        } catch (QuizNotFoundException e){
            return new ResponseEntity<>(new Quiz(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> validateResult(String id, List<Responses> responses) {
        try{
            Quiz quiz = quizRepo.findById(id).orElseThrow(
                    ()->new QuizNotFoundException(id)
            );


            AtomicInteger score = new AtomicInteger();
            responses.forEach(
                    x-> {
                        if(quesService.isCorrectMatch(x.getQuestionId(), x.getAnswer())){
                            score.addAndGet(5);
                        }
                    }
            );
            return new ResponseEntity<>(score, HttpStatus.OK);
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