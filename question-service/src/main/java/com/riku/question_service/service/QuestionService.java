package com.riku.question_service.service;


import com.riku.question_service.model.Question;
import com.riku.question_service.model.QuestionWrapper;
import com.riku.question_service.model.Responses;
import com.riku.question_service.repository.AnswerOnly;
import com.riku.question_service.repository.QuesRepo;
import com.riku.question_service.repository.QuestionIdOnly;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuesRepo repo;

    public ResponseEntity<?> getAllQuestions(){
        try{
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getAllQuestions(int n){
        Pageable limit = PageRequest.of(0, n);
        try{
            return new ResponseEntity<>(repo.findAll(limit), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getAllTitles(){
        try{
            List<Question> allQuestions = repo.findAll();
            List<String> titles = new ArrayList<>();
            allQuestions.forEach(
                    x -> titles.add(x.getTitle())
            );
            return new ResponseEntity<>(titles, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?> addQuestion(Question question) {
        try{
            return new ResponseEntity<>(repo.save(question), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public boolean isCorrectMatch(int questionId, String answer) {
        AnswerOnly actualAnswer = repo.findAnswerByQuestionId(questionId);
        return actualAnswer.getAnswer().equals(answer);
    }

    public ResponseEntity<?> generateQuestionsForQuiz(int numQ) {
        List<Integer> questionsId = repo.findRandomQuestions(numQ)
                .stream()
                .map(QuestionIdOnly::getQuestionId)
                .toList();
        return ResponseEntity.ok(questionsId);
    }

    public ResponseEntity<?> getQuestionsByQuestionId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();

        List<Question> questions = new ArrayList<>();

        for(Integer id : questionIds){
            questions.add(repo.findQuestionByQuestionId(id));
        }

        for(Question q: questions){
            if(q != null) {
                QuestionWrapper wrapper = new QuestionWrapper();
                wrapper.setId(q.getQuestionId());
                wrapper.setTitle(q.getTitle());
                wrapper.setOptions(q.getOptions());
                wrappers.add(wrapper);
            }
        }

        return ResponseEntity.ok(wrappers);
    }


    public ResponseEntity<?> getScore(List<Responses> responses) {
        int score = 0;

        for(Responses res : responses){
            Question q = repo.findQuestionByQuestionId(res.getQuestionId());
            if(res.getAnswer().equals(q.getAnswer())){
                score += 5;
            }
        }
        return ResponseEntity.ok(score);
    }
}
