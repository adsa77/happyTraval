package com.happyTravel.common.config;

import com.happyTravel.common.filter.MDCFilter;
import com.happyTravel.common.filter.RequestResponseLoggingFilter;
import com.happyTravel.common.filter.UserIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration // 이 애너테이션은 이 클래스가 스프링 설정 클래스임을 나타냅니다.
public class FilterConfig {

    // MDCFilter 필터를 등록하는 메서드
    @Bean // 스프링이 이 메서드를 호출해 빈을 등록할 수 있게 합니다.
    public FilterRegistrationBean<MDCFilter> loggingFilter() {
        // FilterRegistrationBean을 사용하여 MDCFilter를 등록
        FilterRegistrationBean<MDCFilter> registrationBean = new FilterRegistrationBean<>();

        // 실제로 필터를 설정 (MDCFilter 사용)
        registrationBean.setFilter(new MDCFilter());

        // 필터를 적용할 URL 패턴 설정. /api/* 경로에만 필터가 적용됨
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        // 필터 설정을 담은 FilterRegistrationBean 반환
        return registrationBean;
    }

    // UserIdFilter 필터를 등록하는 메서드
    @Bean
    public FilterRegistrationBean<UserIdFilter> userIdFilter() {
        // FilterRegistrationBean을 사용하여 UserIdFilter를 등록
        FilterRegistrationBean<UserIdFilter> registrationBean = new FilterRegistrationBean<>();

        // 실제로 필터를 설정 (UserIdFilter 사용)
        registrationBean.setFilter(new UserIdFilter());

        // 필터를 적용할 URL 패턴 설정. /api/* 경로에만 필터가 적용됨
        registrationBean.addUrlPatterns("/api/*");

        // 필터 설정을 담은 FilterRegistrationBean 반환
        return registrationBean;
    }

    // RequestResponseLoggingFilter 필터를 등록하는 메서드
    @Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> requestResponseLoggingFilter() {
        // FilterRegistrationBean을 사용하여 RequestResponseLoggingFilter를 등록
        FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        // 실제로 필터를 설정 (RequestResponseLoggingFilter 사용)
        registrationBean.setFilter(new RequestResponseLoggingFilter());

        // 필터를 적용할 URL 패턴 설정. 모든 URL에 필터가 적용됨
        registrationBean.addUrlPatterns("/*");

        // 필터 체인에서 실행 순서를 지정. 숫자가 작을수록 먼저 실행됨
        registrationBean.setOrder(1);

        // 필터 설정을 담은 FilterRegistrationBean 반환
        return registrationBean;
    }

}
