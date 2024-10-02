package com.happyTravel.common.validation;

import com.happyTravel.common.error.ErrorCode;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//  유효성 검사 로직을 수행할 클래스 지정
@Constraint(validatedBy = UserSignUpDataValidator.class)
//  UserSignUpDtoReq 클래스 전체에 적용
@Target({ ElementType.TYPE })
//  애너테이션의 수명 주기를 정의하기 위한 Retention 임포트. 실행 시에도 애너테이션이 유지됨
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserSignUpData {

    //  기본 검증 실패 메시지
    String message() default "Invalid user data"; // 기본 메시지

    //  검증 그룹 배열 (기본값: 빈 배열)
    Class<?>[] groups() default {};

    //  추가 메타데이터를 위한 페이로드 배열 (기본값: 빈 배열)
    Class<? extends Payload>[] payload() default {};

    //  검증 실패 시 사용할 사용자 정의 에러 코드
    ErrorCode errorCode(); // 에러 코드 추가

}
