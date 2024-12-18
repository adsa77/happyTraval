package com.happyTravel.user.dto;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.validation.idPwd.IdPwdEmptyValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@IdPwdEmptyValidation(errorCode = ErrorCode.VALIDATION_LOGIN_DATA_ERROR)
public class UserLoginDto implements UserIdPwdDto {

    private String userId;
    private String userPwd;

    public UserLoginDto(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }
}
