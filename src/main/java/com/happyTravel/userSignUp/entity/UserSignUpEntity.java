package com.happyTravel.userSignUp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserSignUpEntity {

    @Id
    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    @Column(name = "USER_PWD", length = 50)
    private String userPwd;

    @Column(name = "LAST_LOGIN_DT")
    private LocalDate lastLoginDt;

    @Column(name = "PWD_UPD_DT")
    private LocalDate pwdUpdDt;

    @Column(name = "EMAIL_ID", length = 50)
    private String emailId;

    @Column(name = "EMAIL_ADRESS", length = 50)
    private String emailAdress;

    @Column(name = "PHONE_NO", length = 11)
    private String phoneNo;

    @Column(name = "REG_USER", length = 50)
    private String regUser;

    @Column(name = "REG_DTM")
    private LocalDateTime regDtm = LocalDateTime.now();

    @Column(name = "UPDT_USER", length = 50)
    private String updtUser;

    @Column(name = "UPDT_DTM")
    private LocalDateTime updtDtm;
}
