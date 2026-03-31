package com.riku.quizz.authservice.service;

import com.riku.quizz.authservice.dto.UserRequest;
import com.riku.quizz.authservice.dto.UserResponse;
import com.riku.quizz.authservice.model.User;
import com.riku.quizz.authservice.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo repo;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
    }

    public UserResponse registerUser(UserRequest req) {
        if(repo.existsByEmail(req.getEmail())){
            log.info("User Already Exists!");
            return null;
        }
        return convertToResponse(repo.save(convertToModel(req)));
    }

    private UserResponse convertToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .token(jwtService.generateToken(user))
                .build();
    }

    private User convertToModel(UserRequest req) {
        return User.builder()
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .build();
    }

    public UserResponse login(UserRequest req) {
        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User not Found!"));

        return convertToResponse(user);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
