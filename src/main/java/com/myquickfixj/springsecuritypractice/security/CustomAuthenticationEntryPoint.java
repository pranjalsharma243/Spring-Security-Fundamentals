package com.myquickfixj.springsecuritypractice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myquickfixj.springsecuritypractice.Dto.CustomMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer=response.getWriter();
        CustomMessage customMessage=new CustomMessage();
        customMessage.setMessage("Invalid Details !!"+authException.getMessage());
        customMessage.setStatus(false);
        ObjectMapper objectMapper=new ObjectMapper();
        String responseJson=objectMapper.writeValueAsString(customMessage);
        writer.write(responseJson);
    }
}
