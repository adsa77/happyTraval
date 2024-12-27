package com.happyTravel.partner.dto;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.validation.idPwd.PartnerIdPwdEmptyValidation;
import com.happyTravel.common.validation.idPwd.UserIdPwdEmptyValidation;

@PartnerIdPwdEmptyValidation(errorCode = ErrorCode.VALIDATION_ID_PASSWORD_ERROR)
public interface PartnerIdPwdDto {

    String getUserId();
    String getUserPwd();

}
