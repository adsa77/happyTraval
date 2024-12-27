package com.happyTravel.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyTravel.common.error.ErrorCode;
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
    private final RefreshTokenRepository refreshTokenRepository;
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
            // 기존 리프레시 토큰 조회
            RefreshTokenEntity existingRefreshTokenEntity = refreshTokenRepository.findByUserTypeAndUserId(userType, userId).orElse(null);

            String refreshToken;
            if (existingRefreshTokenEntity == null || isRefreshTokenExpired(existingRefreshTokenEntity)) {
                // 기존 리프레시 토큰이 없거나 만료되었으면 새로 생성
                refreshToken = jwtTokenProvider.createRefreshToken(userId);

                // 새 리프레시 토큰 저장
                refreshTokenService.saveRefreshToken(userType, userId, refreshToken);
            } else {
                // 기존 리프레시 토큰 사용
                refreshToken = existingRefreshTokenEntity.getRefreshToken();
            }

            // JWT 토큰 생성
            String accessToken = jwtTokenProvider.createAccessToken(userId);

            // 응답 객체 생성
            CommonResponse commonResponse = CommonResponse.builder()
                    .message("로그인 성공")
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
                    .message(ErrorCode.SUCCESS_HANDLER_ERROR.getMessage())
                    .httpStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .errorCode(ErrorCode.SUCCESS_HANDLER_ERROR.getCode())
                    .build();

            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            response.flushBuffer();
        }
    }

    private boolean isRefreshTokenExpired(RefreshTokenEntity refreshTokenEntity) {
        // 리프레시 토큰이 만료되었는지 확인하는 로직
        LocalDateTime expiryDate = refreshTokenEntity.getExpiryDate();
        return expiryDate.isBefore(LocalDateTime.now());
    }

}
