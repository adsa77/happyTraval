package com.happyTravel.security.service;

import com.happyTravel.security.entity.RefreshTokenEntity;
import com.happyTravel.security.entity.RefreshTokenEntityPk;
import com.happyTravel.security.jwt.JwtTokenProvider;
import com.happyTravel.security.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.expiration.time}")
    private long expirationTime;

    public RefreshTokenEntity saveRefreshToken(String userType, String userId, String refreshToken) {

        // RefreshToken 만료 시간 설정
        LocalDateTime expiryDate = LocalDateTime.now().plus(Duration.ofMillis(expirationTime));

        // RefreshToken 엔티티 생성 (빌더 사용)
        RefreshTokenEntity token = RefreshTokenEntity.builder()
                .userType(userType)
                .userId(userId)
                .refreshToken(refreshToken)
                .expiryDate(expiryDate)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // RefreshToken을 DB에 저장
        return refreshTokenRepository.save(token);
    }

    public RefreshTokenEntity refreshToken(String userType, String userId, String refreshToken) {
        RefreshTokenEntity token = refreshTokenRepository.findByUserTypeAndUserId(userType, userId)
                .orElseGet(() -> saveRefreshToken(userType, userId, refreshToken));

        // RefreshToken 갱신
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);
        token.setRefreshToken(newRefreshToken);

        // RefreshToken 만료 시간 갱신
        long expirationTime = Long.parseLong(System.getProperty("jwt.refresh.expiration.time"));
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(expirationTime);
        token.setExpiryDate(expiryDate);

        // 갱신된 토큰을 DB에 저장
        token.setUpdatedAt(LocalDateTime.now());
        return refreshTokenRepository.save(token);
    }

}
