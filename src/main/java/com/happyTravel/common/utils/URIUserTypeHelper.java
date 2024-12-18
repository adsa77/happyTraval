package com.happyTravel.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URIUserTypeHelper {

    private static final Logger logger = LoggerFactory.getLogger(URIUserTypeHelper.class);

    /**
     * 요청 URI를 분석하여 사용자 유형을 반환합니다.
     *
     * @param request HTTP 요청 객체
     * @return "ADMIN", "PARTNER", "USER" 중 하나의 사용자 유형
     * @throws IllegalArgumentException 잘못된 URI 요청 시 예외를 발생
     *
     * @author 손지욱
     * @since 24.09.01
     */
    public static String determineUserType(HttpServletRequest request) {
        String uri = request.getRequestURI();

        // URI 로그 추가 (디버깅 용도)
        logger.debug("요청 URI: {}", uri);

        if (uri.contains("/api/admin/login")) {
            return "ADMIN";
        } else if (uri.contains("/api/partner/login")) {
            return "PARTNER";
        } else if (uri.contains("/api/user/login")) {
            return "USER";
        } else {
            // 예외 메시지에 URI 추가하여 구체적으로 명시
            String errorMessage = "잘못된 로그인 요청입니다. 유효한 URI가 아닙니다. 요청 URI: " + uri;
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
