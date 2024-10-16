package com.happyTravel.user.dto;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.validation.ValidEmptyIdPwd;

@ValidEmptyIdPwd(errorCode = ErrorCode.VALIDATION_ID_PASSWORD_ERROR)
public interface UserIdPwdDto {

    String getUserId();
    String getUserPwd();

}
