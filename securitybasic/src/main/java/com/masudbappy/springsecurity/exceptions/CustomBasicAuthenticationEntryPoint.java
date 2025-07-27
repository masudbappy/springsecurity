package com.masudbappy.springsecurity.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        LocalDateTime currentTime = LocalDateTime.now();
        String message = (authException != null && authException.getMessage() !=null)? authException.getMessage(): "Unauthorized access";
        String path = request.getRequestURI();
        response.setHeader("easyBankError", "Authentication failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=UTF-8");
        String jsonResponse = String.format("{\"timestamp\":\"%s\", \"error\": \"%s\"," +
                        " \"message\": \"%s\", \"path\": \"%s\"}", currentTime,
                HttpStatus.UNAUTHORIZED, message, path);
        response.getWriter().write(jsonResponse);
    }
}
