package com.kyaw.jobsearch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class JwtTokenAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private JwtTokenProvider provider;
    @Value("${app.jwt.token}")
    private String token;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        SecurityContext security = SecurityContextHolder.getContext();

        if (security.getAuthentication().isAuthenticated()) {
            // generate token
            String jwtToken = provider.generate(security.getAuthentication().getName(),security.getAuthentication().getAuthorities());

            // add token to header
            ServletServerHttpResponse resp = (ServletServerHttpResponse) serverHttpResponse;
            resp.getServletResponse().setHeader(token,jwtToken);
        }

        return body;
    }
}
