package com.happyTravel.userSignUp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDtoReq {


    @NotBlank(message = "아이디를 입력하세요")
    @Size(min = 4, max = 20, message = "User ID must be between 4 and 20 characters")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$",
            message = "숫자, 영문, 특수문자를 사용해주세요.")
    private String userPwd;

    @Email(message = "이메일은 필수 입니다.")
    private String emailId;

    private String emailAddress;
    private String phoneNo;
    private String regUser;


}
