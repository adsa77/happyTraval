package com.happyTravel.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.security.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication
    ) throws IOException, ServletException {

        String username = authentication.getName();

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(username);
        String refreshToken = jwtTokenProvider.createRefreshToken(username);

        // 응답 객체 생성
        CommonResponse commonResponse = CommonResponse.builder()
                .message("로그인 성공")
                .httpStatus(HttpServletResponse.SC_OK)
                .build();

        commonResponse.addData("accessToken", accessToken);
        commonResponse.addData("refreshToken", refreshToken);

        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
    }

}
