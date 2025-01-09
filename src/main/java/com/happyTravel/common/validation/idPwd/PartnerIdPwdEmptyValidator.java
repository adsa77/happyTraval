package com.happyTravel.common.validation.idPwd;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.partner.dto.PartnerIdPwdDto;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PartnerIdPwdEmptyValidator implements ConstraintValidator<PartnerIdPwdEmptyValidation, PartnerIdPwdDto> {

    private ErrorCode errorCode;

    @Override
    public void initialize(PartnerIdPwdEmptyValidation constraintAnnotation) {
        this.errorCode = constraintAnnotation.errorCode(); // 어노테이션에서 ErrorCode를 가져옴
    }

    @Override
    public boolean isValid(PartnerIdPwdDto dto, ConstraintValidatorContext context) {
        boolean isValid = true;

        //  ID 공백 검사
        if (StringUtils.isBlank(dto.getUserId())) {
            throw new CustomException(ErrorCode.VALIDATION_USER_ID_EMPTY);
        }

        //  비밀번호 공백 검사
        if (StringUtils.isBlank(dto.getUserPwd())) {
            throw new CustomException(ErrorCode.VALIDATION_USER_PASSWORD_EMPTY);
        }

        return true;

    }

}
