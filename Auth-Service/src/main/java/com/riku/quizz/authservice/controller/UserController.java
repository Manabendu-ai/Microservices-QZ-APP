package com.riku.quizz.authservice.controller;


import com.riku.quizz.authservice.dto.UserRequest;
import com.riku.quizz.authservice.dto.UserResponse;
import com.riku.quizz.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> register(
            @RequestBody UserRequest req
    ){
        return ResponseEntity.ok(service.registerUser(req));
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> login(
            @RequestBody UserRequest req
    ){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                )
        );
        return ResponseEntity.ok(service.login(req));
    }

    @GetMapping("/greet/{name}")
    public ResponseEntity<String> greet(@PathVariable String name){
        return ResponseEntity.ok("Hello "+name);
    }
}
