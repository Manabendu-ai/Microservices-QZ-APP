package com.riku.quiz_service.feign;


import com.riku.quiz_service.model.Question;
import com.riku.quiz_service.model.QuestionWrapper;
import com.riku.quiz_service.model.Responses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("QUESTION-SERVICE")

public interface QuizInterface {
    @GetMapping("/question/allQuestions")
    ResponseEntity<?> getAllQuestions();

    @GetMapping("/question/allQuestions/{n}")
    ResponseEntity<?> getAllQuestions(@PathVariable int n);
    @GetMapping("/question/allTitles")
    ResponseEntity<?> getAllTitles();

    @PostMapping("/question/add")
    ResponseEntity<?> addQuestion(@RequestBody Question question);

    @GetMapping("/question/generate")
    ResponseEntity<List<Integer>> generateQuestionsForQuiz(@RequestParam int numQ);

    @PostMapping("/question/getQuestions")
    ResponseEntity<List<QuestionWrapper>> getQuestionsByQuestionId(@RequestBody List<Integer> questionIds);

    @PostMapping("/question/getScore")
    ResponseEntity<?> getScore(@RequestBody List<Responses> responses);

}
