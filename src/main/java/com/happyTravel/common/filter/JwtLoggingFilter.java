//package com.happyTravel.common.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//
///**
// * JwtLoggingFilter는 HTTP 요청에 포함된 JWT 토큰을 로깅하는 필터입니다.
// *
// * 이 필터는 요청 헤더에서 JWT를 추출하여 로깅합니다.
// * JWT가 존재하면 그 값을 로그에 기록하고, 그 후 필터 체인에 요청을 전달합니다.
// *
// * 주로 디버깅 및 보안 분석을 위해 JWT를 로깅하는 데 사용됩니다.
// */
//public class JwtLoggingFilter implements Filter {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtLoggingFilter.class);
//
//    /**
//     * 요청이 들어오면 JWT를 추출하여 로깅하고, 필터 체인에 요청을 전달합니다.
//     *
//     * @param request           서블릿 요청 객체
//     * @param response          서블릿 응답 객체
//     * @param filterChain       필터 체인 객체
//     * @throws IOException      입력/출력 예외
//     * @throws ServletException 서블릿 예외
//     */
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
//            throws IOException, ServletException {
//
//        // 요청에서 JWT 토큰을 추출합니다.
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//
//        // JWT 토큰이 존재하면 로그에 기록합니다.
//        String jwt = resolveToken(httpServletRequest); // JWT 추출 메서드
//        if (StringUtils.hasText(jwt)) {
//            logger.info("JWT received: {}", jwt);
//        }
//
//        // 요청을 다음 필터로 전달합니다.
//        filterChain.doFilter(request, response); // 다음 필터로 요청 전달
//
//    }
//
//    /**
//     * HTTP 요청에서 JWT 토큰을 추출하는 메서드입니다.
//     * 요청 헤더에서 "Authorization"을 가져와 "Bearer "로 시작하면
//     * 그 이후의 부분을 JWT로 반환합니다.
//     *
//     * @param request HTTP 요청 객체
//     * @return JWT 토큰 또는 null (JWT가 없을 경우)
//     */
//    private String resolveToken(HttpServletRequest request) {
//
//        // "Authorization" 헤더에서 "Bearer "로 시작하는 JWT를 추출합니다.
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);    // "Bearer " 이후의 JWT 값만 반환// "Bearer " 이후의 JWT 값만 반환
//        }
//        return null;    // JWT가 없으면 null 반환
//    }
//
//}
