package com.happyTravel.common.validation.termsAgreement;

import com.happyTravel.common.error.ErrorCode;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 회원 가입 시 필수 약관 동의를 검증하기 위한 애너테이션.
 *
 * <p>주로 약관 동의가 필요한 데이터 클래스에 적용됨
 * </p>
 *
 * <ul>
 *     <li>유효성 검사 로직을 수행할 클래스 지정: {@link RequiredAgreementValidator}</li>
 * </ul>
 *
 * @author 손지욱
 * @since 24.09.01
 */
@Constraint(validatedBy = RequiredAgreementValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredAgreementValidation {

    /**
     * 유효성 검사 실패 시 반환될 기본 메시지.
     *
     * @return 기본 오류 메시지
     */
    String message() default "회원 가입 시 필수 약관에 동의해야 합니다.";

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
