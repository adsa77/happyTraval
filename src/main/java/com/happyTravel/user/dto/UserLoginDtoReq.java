package com.happyTravel.user.dto;

import com.happyTravel.common.error.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@ValidUserLoginData(errorCode = ErrorCode.VALIDATION_LOGIN_DATA_ERROR)
public class UserLoginDtoReq implements UserIdPwdDto {

    private String userId;
    private String userPwd;

}
