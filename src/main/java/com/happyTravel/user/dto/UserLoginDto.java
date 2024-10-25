package com.happyTravel.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@ValidUserLoginData(errorCode = ErrorCode.VALIDATION_LOGIN_DATA_ERROR)
public class UserLoginDto implements UserIdPwdDto {

    private String userId;
    private String userPwd;

}
