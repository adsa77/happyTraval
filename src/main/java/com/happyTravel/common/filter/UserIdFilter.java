package com.happyTravel.common.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.happyTravel.common.cache.request.CachedBodyHttpServletRequest;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;

import java.io.IOException;

public class UserIdFilter implements Filter {

    // 잭슨 라이브러리에서 ObjectMapper 가져옮.
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 요청 본문을 캐시하기 위해 CachedBodyHttpServletRequest로 래핑
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpRequest);

        try {
            // 캐시된 요청 본문을 읽고 userId를 추출
            JsonNode jsonBody = objectMapper.readTree(cachedBodyHttpServletRequest.getInputStream());
            // userId가 없으면 null 반환
            String userId = jsonBody.path("userId").asText(null);

            // userId가 null이 아닌 경우에만 MDC에 추가
            if (userId != null) {
                MDC.put("userId", userId); // MDC에 userId 저장
            } else {
                MDC.put("userId", "null"); // userId가 null인 경우 "null" 문자열 저장
            }

            // 다음 필터 또는 서블릿으로 요청 전달
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
        } catch (IOException e) {
            // 요청 본문이 JSON 형식이 아닐 경우 예외 처리
            throw new ServletException("Failed to parse JSON request body", e);
        } finally {
            // 요청 처리 후 MDC에서 userId 제거
//            MDC.remove("userId");
        }
    }
}
