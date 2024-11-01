package com.happyTravel.user.dto;

import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.validation.user.UserSignUpDataValidation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * UserSignUpDto 클래스는 회원가입 시 필요한 사용자 정보를 담는 DTO입니다.
 *
 * <p>사용자가 입력한 ID, 비밀번호, 이메일, 전화번호, 주소 정보와
 * 필수 및 선택 약관 동의 항목을 포함합니다. </p>
 *
 * <p>데이터 유효성을 검증하기 위해 @ValidUserSignUpData 어노테이션이 사용되며,
 * 검증 실패 시 ErrorCode.VALIDATION_USER_SIGNUP_DATA를 반환합니다.</p>
 *
 * <ul>
 *   <li><strong>userId:</strong> 사용자 ID</li>
 *   <li><strong>userPwd:</strong> 사용자 비밀번호</li>
 *   <li><strong>emailId:</strong> 사용자 이메일 아이디</li>
 *   <li><strong>emailDomain:</strong> 사용자 이메일 주소</li>
 *   <li><strong>phoneNo:</strong> 사용자 휴대전화번호</li>
 *   <li><strong>address:</strong> 사용자 주소</li>
 *   <li><strong>requiredAgreements:</strong> 필수 약관 동의 목록</li>
 *   <li><strong>optionalAgreements:</strong> 선택 약관 동의 목록</li>
 * </ul>
 *
 * @author 손지욱
 * @since 24.09.01
 */

@Getter
@Setter
@UserSignUpDataValidation(errorCode = ErrorCode.VALIDATION_USER_SIGNUP_DATA)
public class UserSignUpDto implements UserIdPwdDto {

    private String userId;

    private String userPwd;

    private String emailId;

    private String emailDomain;

    private String phoneNo;

    private String address;

    // 필수 약관 동의 여부 ('T': 동의, 'F': 비동의)
    private List<RequiredTermsAgreementDto> requiredAgreements;

    // 선택 약관 동의 여부 ('T': 동의, 'F': 비동의)
    private List<OptionalTermsAgreementDto> optionalAgreements;

}
