package com.happyTravel.partner.dto;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.validation.partner.PartnerSignUpDataValidation;
import com.happyTravel.common.validation.user.UserSignUpDataValidation;
import com.happyTravel.user.dto.OptionalTermsAgreementDto;
import com.happyTravel.user.dto.RequiredTermsAgreementDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@PartnerSignUpDataValidation(errorCode = ErrorCode.VALIDATION_USER_SIGNUP_DATA)
public class PartnerSignUpDto implements PartnerIdPwdDto {

    private String userId;

    private String userPwd;

    private String emailId;

    private String emailDomain;

    private String phoneNo;

    private String landLinePhoneNo;

    private String businessLicenseNumber;

    private LocalDate pwdUpdDt;

    private String address;

    // 필수 약관 동의 여부 ('T': 동의, 'F': 비동의)
    private List<RequiredTermsAgreementDto> requiredAgreements;

    // 선택 약관 동의 여부 ('T': 동의, 'F': 비동의)
    private List<OptionalTermsAgreementDto> optionalAgreements;

}
