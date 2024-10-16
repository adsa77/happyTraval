package com.happyTravel.user.dto;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.validation.ValidEmptyIdPwd;
import com.happyTravel.common.validation.ValidUserSignUpData;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidUserSignUpData(errorCode = ErrorCode.VALIDATION_USER_SIGNUP_DATA)
public class UserSignUpDtoReq implements UserIdPwdDto {

    private String userId;

    private String userPwd;

    private String emailId;

    private String emailAddress;

    private String phoneNo;

    private String address;

}
