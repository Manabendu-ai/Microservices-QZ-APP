package com.riku.question_service.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Responses {
    private int questionId;
    private String answer;
}
