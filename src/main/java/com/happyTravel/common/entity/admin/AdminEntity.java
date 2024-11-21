package com.happyTravel.common.entity.admin;

import com.happyTravel.common.entity.common.RegUpdtInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ADMIN_TB")
public class AdminEntity {

    @Id
    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    @Column(name = "DEPT_CD", length = 2, nullable = false)
    private String deptCd;  // 외래 키로 사용되는 deptCd

    @Column(name = "USER_PWD", length = 50)
    private String userPwd;

    @Column(name = "LANDLINE_PHONE_NO", length = 12)
    private String landlinePhoneNo;

    @Column(name = "PHONE_NO", length = 11)
    private String phoneNo;

    @Column(name = "OTP_SECRET_KEY", length = 32)
    private String otpSecretKey;

    @Column(name = "PWD_UPD_DT")
    private LocalDate pwdUpdDt;

    @Embedded
    private RegUpdtInfo regUpdtInfo;

    @ManyToOne
    @JoinColumn(name = "DEPT_CD", insertable = false, updatable = false)
    private DeptEntity dept; // 외래 키 연결을 위한 DeptEntity

}
