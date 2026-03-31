package com.riku.api_gateway.filter;

import com.riku.api_gateway.util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
    @Autowired
    private RouteValidator validator;
//    @Autowired
//    private RestTemplate template;

    @Autowired
    private JwtUtil jwtUtil;
    public AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            if(validator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing Authorisation Header!");
                }
                String authHeader =exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }
                try {
//                    template.getForObject(
//                            "http://AUTH-SERVICE//validate?token"+authHeader, String.class
//                    );
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e){
                    log.info("unauthorised access to application!");
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
