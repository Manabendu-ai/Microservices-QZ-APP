package com.riku.question_service.controller;



import com.riku.question_service.model.Question;
import com.riku.question_service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
public class QuestionController {

    private final QuestionService service;

    @GetMapping("/allQuestions")
    public ResponseEntity<?> getAllQuestions(){
        return service.getAllQuestions();
    }

    @GetMapping("/allQuestions/{n}")
    public ResponseEntity<?> getAllQuestions(@PathVariable int n){
        return service.getAllQuestions(n);
    }

    @GetMapping("/allTitles")
    public ResponseEntity<?> getAllTitles(){
        return service.getAllTitles();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody Question question){
        return service.addQuestion(question);
    }
}
