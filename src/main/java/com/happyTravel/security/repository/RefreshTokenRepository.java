package com.happyTravel.security.repository;

import com.happyTravel.security.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {

    // USER_TYPE + USER_ID로 RefreshToken을 조회하는 메서드
    Optional<RefreshTokenEntity> findByUserTypeAndUserId(String userType, String userId);

    // RefreshToken을 저장하는 메서드
    default RefreshTokenEntity saveRefreshToken(RefreshTokenEntity refreshTokenEntity) {
        // 기존의 토큰이 존재하는지 확인
        Optional<RefreshTokenEntity> existingToken = findByUserTypeAndUserId(refreshTokenEntity.getUserType(), refreshTokenEntity.getUserId());

        // 기존 토큰이 있으면 삭제하고 새로운 토큰을 저장
        existingToken.ifPresent(token -> deleteById(token.getUserId()));  // 기존 토큰 삭제

        // 새로운 토큰 저장
        return save(refreshTokenEntity);
    }

    // RefreshToken을 만료일 기준으로 삭제하는 메서드 (예시)
    void deleteByExpiryDateBefore(LocalDateTime expiryDate);

}
