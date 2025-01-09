package com.happyTravel.common.validation.idPwd;

import com.happyTravel.common.error.ErrorCode;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserIdPwdEmptyValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserIdPwdEmptyValidation {

    //  기본 메시지
    String message() default "유효하지 않은 아이디, 비밀번호";
    
    //  검증 그룹 배열 (기본값: 빈 배열)
    Class<?>[] groups() default {};

    //  추가 메타데이터를 위한 페이로드 배열 (기본값: 빈 배열)
    Class<? extends Payload>[] payload() default {};

    //  검증 실패 시 사용할 사용자 정의 에러 코드
    ErrorCode errorCode(); // 에러 코드 추가

}
