package com.happyTravel.security.config;

import com.happyTravel.common.filter.JwtAuthenticationFilter;
import com.happyTravel.security.authentication.CustomAuthenticationProvider;
import com.happyTravel.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.happyTravel.security.jwt.JwtTokenProvider;
import com.happyTravel.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    // 필요한 빈 선언
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    /**
     * 특정 URL 경로에 대한 보안 설정을 무시하는 메서드입니다.
     * /signup, /login, Swagger API 문서 등에 대한 접근을 허용합니다.
     *
     * @return WebSecurityCustomizer 객체로, 보안 필터 체인에서 특정 경로에 대한 보안을 무시하도록 설정합니다.
     */
    @Bean
    public WebSecurityCustomizer ignorePathsInSecurityFilterChain() {
        return (web) -> web.ignoring()
                .requestMatchers("/signup", "/login", "/swagger-ui/**", "/v3/api-docs");
    }

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
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // Form 로그인 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/login", "/api/partner/login", "/api/admin/login").permitAll()
                        .anyRequest().authenticated()
                );

        // user login 경로에 대한 필터 추가
        CustomUsernamePasswordAuthenticationFilter userFilter = new CustomUsernamePasswordAuthenticationFilter(
                authenticationManager(httpSecurity), jwtTokenProvider, customAuthenticationProvider);
        userFilter.setFilterProcessesUrl("/api/user/login");
        httpSecurity.addFilterBefore(userFilter, UsernamePasswordAuthenticationFilter.class);

        // admin login 경로에 대한 필터 추가
        CustomUsernamePasswordAuthenticationFilter adminFilter = new CustomUsernamePasswordAuthenticationFilter(
                authenticationManager(httpSecurity), jwtTokenProvider, customAuthenticationProvider);
        adminFilter.setFilterProcessesUrl("/api/admin/login");
        httpSecurity.addFilterBefore(adminFilter, UsernamePasswordAuthenticationFilter.class);

        // partner login 경로에 대한 필터 추가
        CustomUsernamePasswordAuthenticationFilter partnerFilter = new CustomUsernamePasswordAuthenticationFilter(
                authenticationManager(httpSecurity), jwtTokenProvider, customAuthenticationProvider);
        partnerFilter.setFilterProcessesUrl("/api/partner/login");
        httpSecurity.addFilterBefore(partnerFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }

    /**
     * {@link AuthenticationManager}를 설정하는 메서드입니다.
     * <p>
     * 이 메서드는 {@link HttpSecurity} 객체에서 {@link AuthenticationManagerBuilder}를 가져와서
     * 사용자 인증을 위한 {@link UserDetailsService}와 비밀번호 암호화를 위한 {@link PasswordEncoder}를 설정한 후,
     * 최종적으로 {@link AuthenticationManager} 객체를 생성하여 반환합니다.
     * </p>
     *
     * @param httpSecurity {@link HttpSecurity} 객체로, Spring Security의 보안 설정을 담당합니다.
     * @return {@link AuthenticationManager} 객체로, 사용자 인증을 처리하는 실제 인증 매니저를 반환합니다.
     * @throws Exception 인증 매니저 빌드 중 발생할 수 있는 예외를 처리합니다.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        // HttpSecurity 객체에서 AuthenticationManagerBuilder를 가져옴.
        // HttpSecurity는 Spring Security에서 보안 설정을 담당하는 객체.
        // 여기서 sharedObject()를 호출하여 AuthenticationManagerBuilder를 가져옴.
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        // 사용자 인증을 위한 UserDetailsService와 비밀번호 암호화를 위한 PasswordEncoder를 설정.
        // userDetailsService는 사용자의 세부 정보를 로드하여 인증에 사용.
        // passwordEncoder는 비밀번호를 안전하게 처리.
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        // AuthenticationManager를 빌드하여 반환.
        // AuthenticationManager는 실제 인증 작업을 수행하는 객체.
        return authenticationManagerBuilder.build();
    }

    /**
     * 공통 로그인 필터를 생성하는 메서드입니다.
     */
    private CustomUsernamePasswordAuthenticationFilter createLoginFilter(String loginUrl,
                                                                         AuthenticationManager authenticationManager,
                                                                         JwtTokenProvider jwtTokenProvider) {
        CustomUsernamePasswordAuthenticationFilter filter =
                new CustomUsernamePasswordAuthenticationFilter(authenticationManager, jwtTokenProvider, customAuthenticationProvider);

        filter.setFilterProcessesUrl(loginUrl); // 로그인 경로 설정
        filter.setUsernameParameter("userId"); // 사용자 ID 파라미터 설정
        filter.setPasswordParameter("password"); // 비밀번호 파라미터 설정
        return filter;
    }

    // AuthenticationProvider 등록
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);  // 사용자 인증을 위한 UserDetailsService 설정
        provider.setPasswordEncoder(passwordEncoder);        // 비밀번호 인코딩 설정
        return provider;
    }

}
