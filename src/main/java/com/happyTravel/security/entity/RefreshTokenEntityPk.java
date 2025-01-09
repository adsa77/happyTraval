package com.happyTravel.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Getter;

@Embeddable
@Getter
public class RefreshTokenEntityPk {

    @Id
    @Column(name = "USER_TYPE", length = 1)
    private String userType;

    @Id
    @Column(name = "USER_ID", length = 50)
    private String userId;

    public RefreshTokenEntityPk() {}

    public RefreshTokenEntityPk(String userType, String userId) {
        this.userType = userType;
        this.userId = userId;
    }
}
