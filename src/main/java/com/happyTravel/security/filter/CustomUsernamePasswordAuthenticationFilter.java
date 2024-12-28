package com.happyTravel.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.common.utils.URIUserTypeHelper;
import com.happyTravel.security.authentication.CustomAuthenticationProvider;
import com.happyTravel.security.dto.LoginRequest;
import com.happyTravel.security.handler.CustomAuthenticationFailureHandler;
import com.happyTravel.security.handler.CustomAuthenticationSuccessHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final ObjectMapper objectMapper;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final CustomAuthenticationFailureHandler failureHandler;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        // 로그인 요청 경로 확인 (USER, ADMIN, PARTNER)
        String role;

        try {
            // URIUserTypeHelper를 사용해 역할 결정
            role = URIUserTypeHelper.determineUserType(request);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_REQUEST_PATH);
        }

        String contentType = request.getContentType();
        String userId;
        String userPwd;

        try {
            if (contentType != null && contentType.contains("application/json")) {
                LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
                userId = loginRequest.getUserId();
                userPwd = loginRequest.getUserPwd();
            } else {
                userId = request.getParameter("userId");
                userPwd = request.getParameter("userPwd");
            }
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        // role을 GrantedAuthority로 변환
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        // 인증 수행
        // role을 details에 담아서 전달
        UsernamePasswordAuthenticationToken authRequestToken = new UsernamePasswordAuthenticationToken(userId, userPwd, List.of(authority));
        (authRequestToken).setDetails(role);  // role을 details에 담음

        System.out.println("authority = " + authority);

        return customAuthenticationProvider.authenticate(authRequestToken);
    }

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {

        // 필터에서 인증 성공 후, CustomAuthenticationSuccessHandler를 호출하여 성공 처리
        successHandler.onAuthenticationSuccess(request, response, authResult);
        System.out.println("request = " + request + ", response = " + response + ", authResult = " + authResult);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {

        failureHandler.onAuthenticationFailure(request, response, failed);
    }

}
