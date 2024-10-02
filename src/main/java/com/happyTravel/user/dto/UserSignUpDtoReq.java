package com.happyTravel.user.dto;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.validation.ValidUserSignUpData;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ValidUserSignUpData(errorCode = ErrorCode.VALIDATION_USER_SIGNUP_DATA)
public class UserSignUpDtoReq {

//    @ValidUserSignUpData(errorCode = ErrorCode.VALIDATION_USER_ID_EMPTY)
    private String userId;

//    @ValidUserSignUpData(errorCode = ErrorCode.VALIDATION_USER_PASSWORD_REQUIRED) // 비밀번호 필수 오류 코드
    private String userPwd;

    @NotBlank
    @Email
    private String emailId;

    @NotBlank
    private String emailAddress;

    @NotBlank(message = "전화번호는 필수입니다.") // 전화번호 필수 오류 메시지
    private String phoneNo;

}
