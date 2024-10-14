package com.happyTravel.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(MDCFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // MDC에 추가할 정보 준비
        String requestId = UUID.randomUUID().toString();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestBody = getRequestBody(httpRequest); // 요청 본문 가져오기

        try {
            // 사용자 ID를 요청 헤더에서 가져와 MDC에 추가
            String userId = httpRequest.getHeader("userId");
            MDC.put("userId", userId != null ? userId : "null");

            // 요청 ID 및 URI와 HTTP 메서드를 MDC에 추가
            MDC.put("request_id", requestId);
            MDC.put("uri", httpRequest.getRequestURI());
            MDC.put("httpMethod", httpRequest.getMethod());

            // 필터 체인을 계속 진행
            chain.doFilter(request, response);

            // 응답 상태 저장
            MDC.put("response_status", String.valueOf(httpResponse.getStatus()));
        } finally {
            // 요청 및 응답 정보를 로그에 기록
            logger.info("[REQUEST] [uri: {}] [body: {}] [RESPONSE] [status: {}]",
                    MDC.get("uri"), requestBody, MDC.get("response_status"));
            // 요청이 끝난 후 MDC를 클리어
            MDC.clear();
        }
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
}
