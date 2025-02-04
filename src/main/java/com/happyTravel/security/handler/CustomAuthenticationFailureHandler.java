package com.happyTravel.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.response.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception
    ) throws IOException, ServletException {

        CommonResponse commonResponse = CommonResponse.builder()
                .message(ErrorCode.LOGIN_FAILURE.getMessage())
                .httpStatus(HttpServletResponse.SC_UNAUTHORIZED)
                .errorCode(ErrorCode.LOGIN_FAILURE.getCode())
                .build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
        response.flushBuffer();
    }

}
