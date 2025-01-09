package com.happyTravel.partner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerLoginDto {

    private String userId;
    private String userPwd;

    public PartnerLoginDto(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }

}
