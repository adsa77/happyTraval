package com.happyTravel.common.filter;

import com.happyTravel.common.utils.URIUserTypeHelper;
import com.happyTravel.security.jwt.JwtTokenProvider;
import com.happyTravel.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter는 HTTP 요청에서 JWT 토큰을 추출하고 인증을 수행하는 필터입니다.
 *
 * 이 필터는 요청에 포함된 JWT 토큰을 검사하고, 유효한 토큰이면 해당 사용자 정보를 SecurityContext에 설정하여
 * 이후의 요청에서 인증된 사용자 정보를 사용할 수 있게 합니다.
 *
 * JWT 토큰이 유효한지 검증하고, 인증이 완료되면 SecurityContext에 사용자 정보를 설정하여
 * 인증된 상태로 요청을 처리할 수 있게 만듭니다.
 *
 * @author 손지욱
 * @since 24.09.01
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰을 처리하는 JwtTokenProvider 의존성 주입
    private final UserDetailsServiceImpl userDetailsService; // 사용자 정보 로딩을 위한 UserDetailsService 의존성 주입

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     * HTTP 요청을 처리하고 JWT 토큰을 검증하여 인증 정보를 설정하는 필터 메서드입니다.
     * 유효한 JWT 토큰이 있을 경우, 해당 사용자의 정보를 SecurityContext에 설정하여 인증 상태를 유지합니다.
     *
     * @param request  서블릿 요청 객체
     * @param response 서블릿 응답 객체
     * @param filterChain 필터 체인 객체
     * @throws ServletException 예외 발생 시
     * @throws IOException 입출력 예외 발생 시
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청에서 JWT 토큰을 추출하고, 유효하면 인증 처리
        String token = resolveToken(request); // JWT 추출
        System.out.println("JwtAuthenticationFilter.doFilterInternal");
        System.out.println("@@@token = " + token);
        if (token != null && jwtTokenProvider.validateToken(token)) { // 유효한 토큰인지 확인

            String userId = jwtTokenProvider.getUserIdFromToken(token); // 토큰에서 사용자 ID 추출

            System.out.println("userId = " + userId);

            UserDetails userDetails = userDetailsService.loadUserByUsername(userId); // 사용자 정보 로드

            // URIUserTypeHelper를 사용하여 사용자 유형을 판별
            String userType = URIUserTypeHelper.determineUserType(request);

            // 인증 객체를 생성하여 SecurityContext에 설정
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 정보 설정
        }

        // 요청을 다음 필터로 전달
        filterChain.doFilter(request, response);
    }

    /**
     * HTTP 요청 헤더에서 JWT 토큰을 추출하는 메서드입니다.
     *
     * @param request HTTP 요청 객체
     * @return JWT 토큰 (없으면 null)
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // Authorization 헤더에서 JWT 추출
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 JWT 값만 반환
        }
        return null; // JWT가 없으면 null 반환
    }
}
