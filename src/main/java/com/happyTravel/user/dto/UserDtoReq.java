package com.happyTravel.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoReq {

    //  회원가입
    @NotBlank(message = "아이디를 입력하세요")
    @Size(min = 4, max = 20, message = "User ID must be between 4 and 20 characters")
    private String signUpUserId;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "숫자, 영문, 특수문자를 사용해주세요.")
    private String signUpUserPwd;

    @Email(message = "이메일은 필수 입니다.")
    private String signUpEmailId;

    private String signUpEmailAddress;
    private String signUpPhoneNo;

    //  로그인
    private String loginUserId;
    private String loginUserPwd;

}
