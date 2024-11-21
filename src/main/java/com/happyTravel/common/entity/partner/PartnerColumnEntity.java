package com.happyTravel.common.entity.partner;

import com.happyTravel.common.entity.common.RegUpdtInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "PARTNER_TB")
public class PartnerColumnEntity {

    @Id
    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    @Column(name = "USER_PWD", length = 50)
    private String userPwd;

    @Column(name = "EMAIL_ID", length = 50)
    private String emailId;

    @Column(name = "EMAIL_DOMAIN", length = 50)
    private String emailDomain;

    @Column(name = "LANDLINE_PHONE_NO", length = 16)
    private String landLinePhoneNo;

    @Column(name = "PHONE_NO", length = 11)
    private String phoneNo;

    @Column(name = "BUSINESS_LICENSE_NUMBER", length = 12)
    private String businessLicenseNumber;

    @Column(name = "PWD_UPD_DT")
    private LocalDate pwdUpdDt;

    @Column(name = "ADDRESS", length = 150)
    private String address;

    @Embedded
    private RegUpdtInfo regUpdtInfo;

}
