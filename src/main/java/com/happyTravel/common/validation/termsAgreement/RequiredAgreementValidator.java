package com.happyTravel.common.validation.termsAgreement;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.user.dto.RequiredTermsAgreementDto;
import com.happyTravel.user.dto.UserSignUpDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class RequiredAgreementValidator implements ConstraintValidator<RequiredAgreementValidation, UserSignUpDto> {

    private ErrorCode errorCode;

    @Override
    public void initialize(RequiredAgreementValidation constraintAnnotation) {
        // 사용자 정의 에러 코드 초기화
        this.errorCode = constraintAnnotation.errorCode();
    }

    @Override
    public boolean isValid(UserSignUpDto userSignUpDto, ConstraintValidatorContext context) {
        // userSignUpDto가 null인 경우 검증 통과
        if (userSignUpDto == null) {
            return true; // DTO가 null인 경우 유효성 검사 통과
        }

        // 필수 약관 동의 여부 확인
        List<RequiredTermsAgreementDto> requiredAgreements = userSignUpDto.getRequiredAgreements();

        if (requiredAgreements == null || requiredAgreements.isEmpty()) {
            // 필수 약관 동의 정보가 없을 경우
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.TERMS_CONSENT_MISSING.getMessage())
                    .addConstraintViolation();
            return false; // 유효성 검사 실패
        }

        boolean allAgreed = requiredAgreements.stream()
                .allMatch(agreement -> agreement.getAgreeFl() != null && agreement.getAgreeFl().equals("T"));

        if (!allAgreed) {
            // 검증 실패 시 에러 메시지 설정
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.TERMS_CONSENT_REQUIRED.getMessage())
                    .addConstraintViolation();
            return false; // 유효성 검사 실패
        }

        return true; // 유효성 검사 통과
    }
}
