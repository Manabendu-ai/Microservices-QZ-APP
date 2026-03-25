package com.riku.question_service.controller;



import com.riku.question_service.model.Question;
import com.riku.question_service.model.Responses;
import com.riku.question_service.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Different Types of status code:
    1) Informational responses(100-199)
    2) Successful responses (200-299)
    3) Redirection messages (300-399)
    4) Client side error (400-499)
    5) Server error responses (500-599)
 */

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Tag(name = "Question-API")
public class QuestionController {

    private final QuestionService service;

    @GetMapping("/allQuestions")
    @Operation(summary = "Get all the questions")
    public ResponseEntity<?> getAllQuestions(){
        return service.getAllQuestions();
    }

    @GetMapping("/allQuestions/{n}")
    @Operation(summary = "Get n random questions")
    public ResponseEntity<?> getAllQuestions(@PathVariable int n){
        return service.getAllQuestions(n);
    }

    @GetMapping("/allTitles")
    @Operation(summary = "Get all the titles")
    public ResponseEntity<?> getAllTitles(){
        return service.getAllTitles();
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new Question")
    public ResponseEntity<?> addQuestion(@RequestBody Question question){
        return service.addQuestion(question);
    }

    @GetMapping("/generate")
    @Operation(summary = "Generate Questions for the quiz")
    public ResponseEntity<?> generateQuestionsForQuiz(@RequestParam int numQ){
        return service.generateQuestionsForQuiz(numQ);
    }

    @PostMapping("/getQuestions")
    @Operation(summary = "Get a Question by question id")
    public ResponseEntity<?> getQuestionsByQuestionId(@RequestBody List<Integer> questionIds){
        return service.getQuestionsByQuestionId(questionIds);
    }

    @PostMapping("/getScore")
    @Operation(summary = "Get your total score")
    public ResponseEntity<?> getScore(@RequestBody List<Responses> responses){
        return service.getScore(responses);
    }


}
