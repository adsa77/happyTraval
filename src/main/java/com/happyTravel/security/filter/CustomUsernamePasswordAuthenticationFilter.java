package com.happyTravel.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.security.authentication.CustomAuthenticationProvider;
import com.happyTravel.security.dto.LoginRequest;
import com.happyTravel.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final ObjectMapper objectMapper;

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      JwtTokenProvider jwtTokenProvider,
                                                      CustomAuthenticationProvider customAuthenticationProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        // 로그인 요청 경로 확인
        String path = request.getRequestURI();
        System.out.println("@@@@@@@@@@@@@@@@@path = " + path);

        if (path.equals("/api/user/login")) {
            return processLogin(request, response, "user");
        } else if (path.equals("/api/admin/login")) {
            return processLogin(request, response, "admin");
        } else if (path.equals("/api/partner/login")) {
            return processLogin(request, response, "partner");
        }

        return super.attemptAuthentication(request, response);
    }

    private Authentication processLogin(HttpServletRequest request,
                                        HttpServletResponse response,
                                        String role) throws AuthenticationException {

        // 요청 본문이 JSON인 경우 JSON 파싱 처리
        String contentType = request.getContentType();
        String userId = null;
        String userPwd = null;

        try {
            // JSON 형식인 경우
            if (contentType != null && contentType.contains("application/json")) {
                LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
                userId = loginRequest.getUserId();
                userPwd = loginRequest.getUserPwd();
            } else { // 폼 데이터 처리
                userId = request.getParameter("userId");
                userPwd = request.getParameter("userPwd");
            }
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS); // 예외 처리
        }

        System.out.println("@@@userId = " + userId + ", @@@userPwd = " + userPwd);

        // 인증을 실제로 수행 (CustomAuthenticationProvider 사용)
        Authentication authentication = customAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userId, userPwd));

        if (authentication == null) {
            throw new CustomException(ErrorCode.LOGIN_FAILURE); // 인증 실패
        }

        // 로그인 성공 시 JWT 생성
        String token = jwtTokenProvider.createAccessToken(userId); // 사용자 ID로 액세스 토큰 생성
        String refreshToken = jwtTokenProvider.createRefreshToken(userId); // 리프레시 토큰 생성

        // JWT 토큰을 응답 헤더로 설정 (클라이언트에 전달)
        response.setHeader("Authorization", "Bearer " + token); // Bearer Token 방식으로 전달
        response.setHeader("Refresh-Token", refreshToken); // Refresh Token도 필요하다면 전달

        return authentication; // 인증 객체 반환
    }

}
