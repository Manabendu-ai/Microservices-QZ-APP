package com.riku.api_gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openAPIEndPoints = List.of(
            "/auth/signup",
            "/auth/signin",
            "/auth/greet/{name}"
    );

    public Predicate<ServerHttpRequest> isSecured =
            req -> openAPIEndPoints
                    .stream()
                    .noneMatch(uri -> req.getURI().getPath().contains(uri));
}
