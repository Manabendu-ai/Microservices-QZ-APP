package com.riku.quizz.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
