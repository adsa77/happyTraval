package com.happyTravel.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyTravel.common.cache.request.CachedBodyHttpServletRequest;
import com.happyTravel.common.cache.response.CachedBodyHttpServletResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        // Swagger 경로 필터 제외 설정
//        String path = request.getRequestURI();
//        return path.startsWith("/swagger") || path.startsWith("/v3/api-docs");
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //  요청 본문 읽기
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);
        String requestBody = new String(cachedRequest.getContentAsByteArray());
        CachedBodyHttpServletResponse cachedResponse = new CachedBodyHttpServletResponse(response);

        // 요청 본문 설정
        MDC.put("requestBody", requestBody);

        try {
            // 필터 체인 진행
            chain.doFilter(cachedRequest, cachedResponse);

            // 응답 본문 설정
            String responseBody = new String(cachedResponse.getContentAsByteArray());
            MDC.put("responseBody", responseBody);

        } finally {
            // 필터에서 MDC 값은 MDCFilter에서 로그 처리에 활용됨
            MDC.clear();
        }
    }
}
