package com.happyTravel.common.validation;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.user.dto.UserSignUpDtoReq;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserSignUpDataValidator implements ConstraintValidator<ValidUserSignUpData, UserSignUpDtoReq> {

    private ErrorCode errorCode;

    @Override
    public void initialize(ValidUserSignUpData constraintAnnotation) {
        this.errorCode = constraintAnnotation.errorCode(); // 어노테이션에서 ErrorCode를 가져옴
    }

    @Override
    public boolean isValid(UserSignUpDtoReq signUpDto, ConstraintValidatorContext context) {
        // ID 유효성 검사
        if (StringUtils.isBlank(signUpDto.getUserId())) {
            throw new CustomException(ErrorCode.VALIDATION_USER_ID_EMPTY);
        }
        if (signUpDto.getUserId().length() < 4 || signUpDto.getUserId().length() > 20) {
            throw new CustomException(ErrorCode.USER_ID_LENGTH);
        }

        // 비밀번호 유효성 검사
        if (StringUtils.isBlank(signUpDto.getUserPwd())) {
            throw new CustomException(ErrorCode.VALIDATION_USER_PASSWORD_REQUIRED);
        }
        if (signUpDto.getUserPwd().length() < 8 || signUpDto.getUserPwd().length() > 20) {
            throw new CustomException(ErrorCode.VALIDATION_PASSWORD_REQUIREMENTS_NOT_MET);
        }

        // 비밀번호 확인
//        if (!signUpDto.getUserPwd().equals(signUpDto.getConfirmUserPwd())) { // confirmUserPwd는 추가된 필드라고 가정
//            throw new CustomException(ErrorCode.VALIDATION_PASSWORD_MISMATCH);
//        }

        // 이메일 유효성 검사 (추가 필요에 따라)
//        if (StringUtils.isBlank(signUpDto.getEmailId())) {
//            throw new CustomException(ErrorCode.VALIDATION_PHONE_NUMBER_REQUIRED);
//        }

        // 전화번호 유효성 검사 (추가 필요에 따라)
//        if (StringUtils.isBlank(signUpDto.getPhoneNo())) {
//            throw new CustomException(ErrorCode.VALIDATION_PHONE_NUMBER_REQUIRED);
//        }

        return true;

    }

}
