package com.happyTravel.userSignUp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDtoResp {

    private String message;  // 예: "회원가입이 완료되었습니다."
    private String userId;   // 생성된 사용자 ID

}
