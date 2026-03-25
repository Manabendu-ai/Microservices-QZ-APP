package com.riku.question_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig(){
        return new OpenAPI()
                .info(
                new Info()
                        .title("Question Service APIs")
                        .description("By Manabendu Karfa")
                )
                .servers(Arrays.asList(
                        new Server().url("http://localhost:8081"),
                        new Server().url("http://localhost:8082").description("Quiz Service")
                ));

    }
}
