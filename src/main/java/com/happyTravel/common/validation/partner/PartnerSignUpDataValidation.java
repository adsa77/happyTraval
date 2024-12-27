package com.happyTravel.common.validation.partner;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.validation.user.UserSignUpDataValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PartnerSignUpDataValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PartnerSignUpDataValidation {

    /**
     * 유효성 검사 실패 시 반환될 기본 메시지.
     *
     * @return 기본 오류 메시지
     */
    String message() default "유효하지 않은 로그인 데이터";

    /**
     * 유효성 검증을 그룹으로 구분할 때 사용되는 그룹 배열.
     *
     * @return 기본값은 빈 배열
     */
    Class<?>[] groups() default {};

    /**
     * 추가 메타데이터를 제공하기 위해 사용되는 페이로드 배열.
     *
     * @return 기본값은 빈 배열
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 검증 실패 시 사용할 사용자 정의 에러 코드.
     *
     * @return ErrorCode 열거형 값
     */
    ErrorCode errorCode();
}
