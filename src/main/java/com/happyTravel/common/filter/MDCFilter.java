package com.happyTravel.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            // 사용자 ID를 요청 헤더에서 가져와 MDC에 추가
            String userId = httpRequest.getHeader("X-User-ID");
            if (userId != null) {
                MDC.put("userId", userId);
            }

            // 요청 ID를 생성하여 MDC에 추가
            UUID uuid = UUID.randomUUID();
            MDC.put("request_id", uuid.toString());

            // 필터 체인을 계속 진행
            chain.doFilter(request, response);
        } finally {
            // 요청이 끝난 후 MDC를 클리어
            MDC.clear();
        }
    }


}
