package com.riku.quiz_service.controller;

import com.riku.QuizzAPP.model.Responses;
import com.riku.QuizzAPP.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(
            @RequestParam int numQ,
            @RequestParam String title
    ){
        return quizService.createQuiz(numQ, title);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getQuizById(@PathVariable String id){
        return quizService.getQuizById(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<?> SubmitQuizz(
            @PathVariable String id,
            @RequestBody List<Responses> responses
    ){
        return quizService.validateResult(id, responses);
    }



}
