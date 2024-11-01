package com.happyTravel.common.validation.user;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.user.dto.UserSignUpDto;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 회원 가입 데이터 유효성 검사를 수행하는 클래스.
 *
 * <p> {@link UserSignUpDataValidation} 애너테이션에 의해 지정된 {@link UserSignUpDto} 객체에 대한 유효성 검사를 처리한다.</p>
 *
 * @author 손지욱
 * @since 24.09.01
 */
public class UserSignUpDataValidator implements ConstraintValidator<UserSignUpDataValidation, UserSignUpDto> {

    private ErrorCode errorCode; // 사용자 정의 오류 코드를 저장하는 변수

    /**
     * 초기화 메서드.
     *
     * @param constraintAnnotation {@link UserSignUpDataValidation} 애너테이션 인스턴스
     */
    @Override
    public void initialize(UserSignUpDataValidation constraintAnnotation) {
        this.errorCode = constraintAnnotation.errorCode(); // 어노테이션에서 ErrorCode를 가져옴
    }

    /**
     * 유효성 검사 수행.
     *
     * @param signUpDto 검사할 {@link UserSignUpDto} 객체
     * @param context 유효성 검사 컨텍스트
     * @return 유효성 검사 결과, 모든 검사가 통과하면 true 반환
     * @throws CustomException 유효성 검사 실패 시 사용자 정의 예외
     */
    @Override
    public boolean isValid(UserSignUpDto signUpDto, ConstraintValidatorContext context) {

        // ID 유효성 검사
        if (signUpDto.getUserId().length() < 4 || signUpDto.getUserId().length() > 20) {
            throw new CustomException(ErrorCode.USER_ID_LENGTH);
        }

        // 비밀번호 유효성 검사
        if (StringUtils.isBlank(signUpDto.getUserPwd())) {
            throw new CustomException(ErrorCode.VALIDATION_USER_PASSWORD_REQUIRED);
        }

        if (signUpDto.getUserPwd().length() < 8) {
            throw new CustomException(ErrorCode.PASSWORD_LENGTH_REQUIREMENTS_NOT_MET);
        }

        if (!signUpDto.getUserPwd().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$")) {
            throw new CustomException(ErrorCode.VALIDATION_PASSWORD_REQUIREMENTS_NOT_MET);
        }

        // 비밀번호 확인 필드 검사 (추가 필요 시 주석 해제)
        // if (!signUpDto.getUserPwd().equals(signUpDto.getConfirmUserPwd())) {
        //     throw new CustomException(ErrorCode.VALIDATION_PASSWORD_MISMATCH);
        // }

        // 이메일 유효성 검사 (추가 필요에 따라 주석 해제)
        // if (StringUtils.isBlank(signUpDto.getEmailId())) {
        //     throw new CustomException(ErrorCode.VALIDATION_PHONE_NUMBER_REQUIRED);
        // }

        // 전화번호 유효성 검사
        if (StringUtils.isBlank(signUpDto.getPhoneNo())) {
            throw new CustomException(ErrorCode.VALIDATION_PHONE_NUMBER_REQUIRED);
        }

        // 주소 유효성 검사
        if (StringUtils.isBlank(signUpDto.getAddress())) {
            throw new CustomException(ErrorCode.VALIDATION_ADDRESS_REQUIRED); // 주소 필수 오류 코드
        }
        if (signUpDto.getAddress().length() > 150) {
            throw new CustomException(ErrorCode.VALIDATION_ADDRESS_LENGTH); // 주소 길이 오류 코드
        }

        return true; // 모든 검사가 통과하면 true 반환
    }

}
