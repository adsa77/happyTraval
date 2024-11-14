package com.happyTravel.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 설정 클래스.
 *
 * <p>이 클래스는 비밀번호 암호화 등 보안 관련 빈을 정의합니다.
 * {@link BCryptPasswordEncoder}를 사용하여 비밀번호 암호화를 위한 {@link PasswordEncoder} 빈을 제공합니다.
 *
 * @author 손지욱
 * @since 24.09.01
 */
@Configuration
public class PasswordConfig {

    /**
     * BCrypt 해싱 알고리즘을 사용하는 {@link PasswordEncoder} 빈을 생성합니다.
     *
     * <p>BCrypt는 비밀번호 암호화에 적합한 보안성 높은 해싱 함수로,
     * 각기 다른 해시값을 생성하여 보안성을 강화합니다.
     *
     * @return 비밀번호를 암호화하는 {@link PasswordEncoder} 인스턴스
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
