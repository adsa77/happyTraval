package com.happyTravel.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor  //매개변수가 없는 기본 생성자를 자동으로 생성
@AllArgsConstructor //모든 필드를 인자로 받는 생성자를 생성
@Builder            //빌더 패턴 사용
@Table(name = "USER_TB")
public class UserColumnEntity {

    @Id
    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    @Column(name = "USER_PWD", length = 60)
    private String userPwd;

    @Column(name = "LAST_LOGIN_DT")
    private LocalDate lastLoginDt;

    @Column(name = "PWD_UPD_DT")
    private LocalDate pwdUpdDt;

    @Column(name = "EMAIL_ID", length = 50)
    private String emailId;

    @Column(name = "EMAIL_ADDRESS", length = 50)
    private String emailAddress;

    @Column(name = "PHONE_NO", length = 11)
    private String phoneNo;

    @Column(name = "ADDRESS", length = 150)
    private String address;

    @Embedded
    private RegUpdtInfo regUpdtInfo;

}
