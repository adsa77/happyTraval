package com.happyTravel.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyTravel.common.request.CachedBodyHttpServletRequest;
import com.happyTravel.common.response.CachedBodyHttpServletResponse;
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

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 요청 본문 읽기
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
        String requestBody = new String(cachedBodyHttpServletRequest.getContentAsByteArray());

        // 요청 로그 기록
//        logger.info("[REQUEST] [uri: {}] [body: {}]", request.getRequestURI(), requestBody);

        // 응답을 캐싱하기 위해 WrappedResponse 생성
        CachedBodyHttpServletResponse cachedBodyHttpServletResponse = new CachedBodyHttpServletResponse(response);

        try {
            // 다음 필터로 요청 전달
            chain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);
        } finally {
            // 응답 본문 읽기
            String responseBody = new String(cachedBodyHttpServletResponse.getContentAsByteArray());

            // 응답 로그 기록
            logger.info("[RESPONSE] [uri: {}] [status: {}] [body: {}]", request.getRequestURI(), response.getStatus(), responseBody);
        }
    }
}
