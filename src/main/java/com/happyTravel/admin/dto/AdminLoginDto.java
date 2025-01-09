package com.happyTravel.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginDto {

    private String userId;
    private String userPwd;

    public AdminLoginDto(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }

}
