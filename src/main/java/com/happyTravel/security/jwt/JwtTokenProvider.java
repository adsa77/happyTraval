package com.happyTravel.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * JwtTokenProvider는 JWT(Json Web Token)를 생성, 검증, 및 파싱하는 기능을 제공합니다.
 * JWT는 사용자 인증 및 세션 관리를 위한 토큰 기반 인증 메커니즘입니다.
 * 이 클래스는 JWT 액세스 토큰과 리프레시 토큰을 생성하고, 토큰에서 사용자 정보를 추출하거나 유효성을 검증하는 기능을 제공합니다.
 *
 * @author 손지욱
 * @since 24.09.01
 */
@Slf4j
@Component
public class JwtTokenProvider {

    /**
     * JWT Secret Key 객체.
     * <p>
     * HMAC을 사용하는 현재 방식에서는 `SecretKey` 타입이 적합하지만,
     * 향후 다른 암호화 알고리즘이 필요할 수 있으므로 `Key` 타입을 사용하여 범용성을 확보합니다.
     * 실제로 사용할 때는 `SecretKey`로 형변환하여 사용할 예정입니다.
     */
    private Key key;

    /**
     * JWT 서명에 사용할 비밀 키.
     * <p>
     * application.properties 또는 application.yml에서 설정된 값을 가져옵니다.
     */
    @Value("${jwt.secret.key}")
    private String secretKey;

    /**
     * JWT의 유효기간 (밀리초 단위).
     * <p>
     * application.properties 또는 application.yml에서 설정된 값을 가져옵니다.
     */
    @Value("${jwt.expiration.time}")
    private long accessTokenExpirationTime;

    /**
     * JWT 리프레시 토큰의 유효기간 (밀리초 단위).
     * <p>
     * application.properties 또는 application.yml에서 설정된 값을 가져옵니다.
     */
    @Value("${jwt.refresh.expiration.time}")
    private long refreshTokenExpirationTime;

    /**
     * 초기화 메서드로 Base64로 인코딩된 시크릿 키를 디코딩하고 Key 객체를 생성합니다.
     * 이 메서드는 `@PostConstruct`로 애플리케이션이 시작될 때 자동으로 호출됩니다.
     */
    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(decodedKey); // HMAC SHA 알고리즘용 Key 생성
    }

    /**
     * 사용자 ID를 기반으로 액세스 JWT를 생성합니다.
     * <p>
     * 주어진 사용자 ID를 기반으로 JWT 토큰을 생성하며, 만료 시간을 설정합니다.
     * </p>
     *
     * @param userId 사용자 ID (JWT의 subject로 사용됨).
     * @return 생성된 JWT 문자열.
     */
    public String createAccessToken(String authority, String userId){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationTime);

        return Jwts.builder()
                .claim("sub", userId) // JWT의 subject 설정 (주로 사용자 ID)
                .claim("authority", authority)   // 권한 정보 추가
                .issuedAt(now)              // 발급 시간 설정
                .expiration(expiryDate)     // 만료 시간 설정
                .signWith(key)              // HS512 알고리즘 사용
                .compact();                 // 최종 JWT 문자열 반환
    }

    /**
     * 사용자 ID를 기반으로 리프레시 JWT를 생성합니다.
     * <p>
     * 주어진 사용자 ID를 기반으로 리프레시 JWT 토큰을 생성하며, 만료 시간을 설정합니다.
     * </p>
     *
     * @param userId 사용자 ID (JWT의 subject로 사용됨).
     * @return 생성된 리프레시 JWT 문자열.
     */
    public String createRefreshToken(String authority, String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpirationTime);

        return Jwts.builder()
                .claim("sub", userId)
                .claim("authority", authority)   // 권한 정보 추가
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * JWT에서 사용자 ID를 추출합니다.
     * <p>
     * .verifyWith((SecretKey) key): 서명 검증에 사용할 키를 설정합니다. 이때, Key 타입으로 선언된 key를
     * SecretKey로 형변환하여 사용합니다.
     * </p>
     * <p>
     * .build(): JwtParser 객체를 생성합니다.
     * </p>
     * <p>
     * .parseSignedClaims(token): 전달된 JWT 토큰을 파싱하여 Claims 객체를 반환합니다.
     * </p>
     * <p>
     * .getPayload(): 파싱된 Claims 객체에서 payload를 추출합니다.
     * </p>
     * <p>
     * claims.getSubject(): 추출된 Claims 객체에서 subject 필드를 사용하여 사용자 ID를 반환합니다.
     * </p>
     *
     * @param token 사용자로부터 받은 JWT.
     * @return JWT에서 추출된 사용자 ID.
     */

    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) key)  // 서명 검증에 사용할 키 설정
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();  // Payload 반환

            return claims.getSubject();  // 사용자 ID는 subject에 포함됩니다.
        } catch (Exception e) {
            log.error("JWT 토큰 파싱 오류: ", e);
            return null;  // 예외 발생 시 null 반환 (필요시 예외 던질 수도 있음)
        }
    }

    /**
     * JWT 토큰에서 사용자 유형을 추출하는 메서드입니다.
     *
     * @param token JWT 토큰
     * @return 사용자 유형 (ADMIN, PARTNER, USER)
     */
    public String getAuthorityFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("authority", String.class); // JWT claims에서 "authority"를 추출
    }

    /**
     * JWT의 유효성을 검증합니다.
     *
     * @param token 사용자로부터 받은 JWT.
     * @return JWT가 유효하면 true, 그렇지 않으면 false.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token);
            return true;                        // 파싱 성공 시 유효한 토큰으로 판단
        } catch (Exception e) {
            return false;                       // 예외 발생 시 유효하지 않은 토큰으로 판단
        }
    }

}
