package com.happyTravel.common.validation.partner;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.common.validation.user.UserSignUpDataValidation;
import com.happyTravel.partner.dto.PartnerSignUpDto;
import com.happyTravel.user.dto.UserSignUpDto;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PartnerSignUpDataValidator implements ConstraintValidator<PartnerSignUpDataValidation, PartnerSignUpDto> {

    private ErrorCode errorCode; // 사용자 정의 오류 코드를 저장하는 변수

    @Override
    public void initialize(PartnerSignUpDataValidation constraintAnnotation) {
        this.errorCode = constraintAnnotation.errorCode(); // 어노테이션에서 ErrorCode를 가져옴
    }

    @Override
    public boolean isValid(PartnerSignUpDto signUpDto, ConstraintValidatorContext context) {

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
