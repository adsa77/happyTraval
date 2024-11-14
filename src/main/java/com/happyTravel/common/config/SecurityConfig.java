package com.happyTravel.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정을 위한 클래스입니다.
 *
 * 이 클래스는 Spring Security의 보안 필터 체인을 정의하고, HTTP 요청에 대한 인증 및 권한을 설정합니다.
 * 기본적인 보안 설정을 구성하며, CSRF 보호 비활성화, 기본 폼 로그인 비활성화, HTTP Basic 인증 비활성화 등을 처리합니다.
 * 또한, 특정 URL에 대한 공개 접근을 허용하는 설정도 포함됩니다.
 *
 * @see HttpSecurity
 * @see SecurityFilterChain
 * @see AbstractHttpConfigurer
 *
 * @author 손지욱
 * @since 24.09.01
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Spring Security의 HTTP 요청에 대한 보안 정책을 설정하는 메서드입니다.
     * 이 메서드는 HTTP 요청에 대한 접근 제어 및 다양한 보안 설정을 구성합니다.
     *
     * @param httpSecurity HttpSecurity 객체로, HTTP 요청에 대한 보안 설정을 담당합니다.
     * @return SecurityFilterChain 객체로, HTTP 요청에 대한 보안 필터를 적용합니다.
     * @throws Exception 보안 설정 중 발생할 수 있는 예외를 처리합니다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // HTTP 요청에 대한 접근 제어 설정
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                // 공개 URL 설정
                                .requestMatchers("/signup", "/login", "/swagger-ui/**", "/v3/api-docs").permitAll()
                                // 나머지 요청도 모두 허용
                                .anyRequest().permitAll()
                        )
                //  람다식
                // CSRF 보호 비활성화 (Web 애플리케이션에서 POST 요청을 쉽게 처리하기 위해 비활성화)
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                // 폼 로그인 비활성화 (폼 로그인을 사용하지 않도록 설정)
                .formLogin(formLogin -> formLogin.disable())  // 폼 로그인 비활성화
                // HTTP Basic 인증 비활성화 (기본 인증을 사용하지 않도록 설정)
                .httpBasic(httpBasic -> httpBasic.disable());  // HTTP Basic 인증 비활성화

                //  메서드 참조
//                .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화
//                .formLogin(AbstractHttpConfigurer::disable)  // 폼 로그인 비활성화
//                .httpBasic(AbstractHttpConfigurer::disable);  // HTTP Basic 인증 비활성화
        

        return httpSecurity.build();
    }

}
