package com.happyTravel.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCFilter implements Filter {

    //  요청과 응답의 메타데이터(예: HTTP 메서드, URI, 응답 내용 등)를 로깅
    //  시스템에서 발생하는 이벤트나 오류를 기록
    private static final Logger logger = LoggerFactory.getLogger(MDCFilter.class);

    //  요청과 응답을 처리
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //  응답 내용을 메모리에 저장하기 위한 출력 스트림
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        try {
            // HttpServletRequest 및 HttpServletResponse 변환
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;


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
