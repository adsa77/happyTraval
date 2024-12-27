package com.happyTravel.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.security.entity.RefreshTokenEntity;
import com.happyTravel.security.jwt.JwtTokenProvider;
import com.happyTravel.security.repository.RefreshTokenRepository;
import com.happyTravel.security.service.RefreshTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final RefreshTokenService refreshTokenService;

    /**
     * 로그인 성공 시 호출되는 메서드로 JWT 토큰을 생성하여 응답에 포함시킵니다.
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @param authentication 인증 정보
     * @throws IOException 입력 출력 예외 처리
     * @throws ServletException 서블릿 예외 처리
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // 인증된 사용자 이름 추출
        String userId = authentication.getName();
        String userType = (String) authentication.getDetails();

        try {
            // JWT 토큰 생성
            String accessToken = jwtTokenProvider.createAccessToken(userId);
            String refreshToken = jwtTokenProvider.createRefreshToken(userId);

            // Refresh Token 저장
            refreshTokenService.saveRefreshToken(userType, userId, refreshToken);

            // 응답 객체 생성
            CommonResponse commonResponse = CommonResponse.builder()
                    .message("로그인 성공s")
                    .httpStatus(HttpServletResponse.SC_OK)
                    .build();

            // 응답에 토큰 데이터 추가
            commonResponse.addData("accessToken", accessToken);
            commonResponse.addData("refreshToken", refreshToken);


            // 응답 헤더 및 본문 설정
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
            response.flushBuffer();

        } catch (Exception e) {
            // 예외 처리
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");

            // CommonResponse 객체를 사용하여 오류 메시지 전달
            CommonResponse errorResponse = CommonResponse.builder()
                    .message("로그인 처리 중 오류 발생. onAuthenticationSuccess")
                    .httpStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .build();

            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            response.flushBuffer();
        }
    }
}
