package com.happyTravel.common.utils;

import jakarta.servlet.http.HttpServletRequest;

public class URIUserTypeHelper {

    public static String determineUserType(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri.contains("/api/admin")) {
            return "ADMIN";
        } else if (uri.contains("/api/partner")) {
            return "PARTNER";
        }
        return "USER";
    }
}
