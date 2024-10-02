package com.happyTravel.common.validation;

import com.happyTravel.common.error.ErrorCode;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserSignUpDataValidator.class)
@Target({ ElementType.TYPE }) // UserSignUpDtoReq 클래스 전체에 적용
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserSignUpData {

    String message() default "Invalid user data"; // 기본 메시지

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    ErrorCode errorCode(); // 에러 코드 추가

}
