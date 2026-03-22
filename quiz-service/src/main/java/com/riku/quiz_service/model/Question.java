package com.riku.quiz_service.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "questions")
@Data
public class Question {

    @Id
    private String id;
    private int questionId;
    private String title;
    private List<String> options;
    private String answer;

}
