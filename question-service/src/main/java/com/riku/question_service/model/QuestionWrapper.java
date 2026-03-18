package com.riku.question_service.model;

import lombok.Data;

import java.util.List;

@Data
public class QuestionWrapper {

    private int id;
    private String title;
    private List<String> options;
}
