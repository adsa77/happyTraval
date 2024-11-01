package com.happyTravel.common.validation.user;

import com.happyTravel.common.error.ErrorCode;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * UserSignUpDto 클래스의 데이터를 유효성 검증하기 위해 사용하는 애너테이션임.
 * {@link UserSignUpDataValidator} 클래스를 통해 커스텀 유효성 검증 로직을 적용하며,
 * 클래스 레벨에 적용하여 전체 데이터 객체가 검증되도록 함.
 *
 * <p>이 애너테이션을 사용하려면 UserSignUpDto 클래스에 선언하여 전체 데이터를 검증함.
 * 기본 메시지와 에러 코드, 그룹 등을 설정할 수 있음.
 * </p>
 *
 * @author 손지욱
 * @since 24.09.01
 */

/**
 * UserSignUpDto 클래스의 데이터를 유효성 검증하기 위해 사용하는 애너테이션임.
 * <ul>
 *     <li>유효성 검사 로직을 수행할 클래스 지정: {@link UserSignUpDataValidator}</li>
 *     <li>UserSignUpDto 클래스 전체에 적용</li>
 *     <li>애너테이션의 수명 주기를 정의하기 위한 Retention: 실행 시에도 애너테이션이 유지됨</li>
 * </ul>
 */
@Constraint(validatedBy = UserSignUpDataValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserSignUpDataValidation {

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
