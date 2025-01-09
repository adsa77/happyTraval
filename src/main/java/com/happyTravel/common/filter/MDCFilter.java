package com.happyTravel.common.filter;

import com.happyTravel.common.cache.request.CachedBodyHttpServletRequest;
import com.happyTravel.common.cache.response.CachedBodyHttpServletResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(MDCFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        // UUID를 사용하여 요청 ID 생성
        String requestId = UUID.randomUUID().toString();
        // 요청, 응답 형변환
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // 사용자 ID를 요청 헤더에서 가져옴
        String userId = httpServletRequest.getHeader("userId");

        // MDC 설정
        // 요청 ID 추가
        MDC.put("request_id", requestId);
        // 요청 URI 추가
        MDC.put("uri", httpServletRequest.getRequestURI());
        // HTTP 메서드 추가
        MDC.put("httpMethod", httpServletRequest.getMethod());
        // userId가 있으면 추가, 없으면 null 설정
        MDC.put("userId", userId != null ? userId : "null");

        // 요청 본문을 캐시하기 위한 WrappedRequest 생성
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        // 응답을 캐싱하기 위한 WrappedResponse 생성
        CachedBodyHttpServletResponse cachedBodyHttpServletResponse = new CachedBodyHttpServletResponse(httpServletResponse);

        try {
            // 필터 체인 진행
            filterChain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);

//            // 요청 본문 읽기 전에 MDC에 설정된 userId와 uri 값이 제대로 반영되어야 한다
//            MDC.put("userId", userId != null ? userId : "null");
//            MDC.put("uri", httpServletRequest.getRequestURI());

            // 요청 본문 읽기
            String requestBody = new String(cachedBodyHttpServletRequest.getContentAsByteArray());
            MDC.put("requestBody", requestBody);  // 요청 본문 저장

            // 요청 로그 타입 설정
            MDC.put("logType", "요청로그");
            logger.info("[REQUEST] [uri: {}] [body: {}]", MDC.get("uri"), MDC.get("requestBody"));

            // 처리 완료 후 응답 로그 타입 설정
            MDC.put("logType", "응답로그");
            String responseBody = new String(cachedBodyHttpServletResponse.getContentAsByteArray()); // 응답 본문 읽기
            MDC.put("response_status", String.valueOf(cachedBodyHttpServletResponse.getStatus())); // 응답 상태 코드 저장

            // 응답 로그 기록
            logger.info("[RESPONSE] [uri: {}] [status: {}] [body: {}]", MDC.get("uri"), MDC.get("response_status"), responseBody);
        } catch (Exception ex) {
            // 예외 발생 시 예외 로그 타입 설정
            MDC.put("logType", "예외로그");
            logger.error("[EXCEPTION] [message: {}]", ex.getMessage(), ex);
            throw ex;  // 예외 재발생
        } finally {
            MDC.clear();  // MDC 클리어
        }
    }
}
