package com.happyTravel.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor  //매개변수가 없는 기본 생성자를 자동으로 생성
@AllArgsConstructor //모든 필드를 인자로 받는 생성자를 생성
@Builder            //빌더 패턴 사용
@Table(name = "USER_TB")
public class UserColumnEntity {

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

    @Column(name = "EMAIL_ADDRESS", length = 50)
    private String emailAddress;

    @Column(name = "PHONE_NO", length = 11)
    private String phoneNo;

    @Column(name = "REG_USER", length = 50)
    private String regUser;

    @Column(name = "REG_DTM")
    @Builder.Default
    private LocalDateTime regDtm = LocalDateTime.now();

    @Column(name = "UPDT_USER", length = 50)
    private String updtUser;

    @Column(name = "UPDT_DTM")
    private LocalDateTime updtDtm;

}
