package com.happyTravel.partner.dto;

import com.happyTravel.common.entity.pk.RequiredTermsAgreePk;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.validation.termsAgreement.RequiredAgreementValidation;
import lombok.Getter;
import lombok.Setter;

/**
 * RequiredTermsAgreementDto는 필수 약관 동의 정보를 담는 DTO입니다.
 *
 * @author 손지욱
 * @since 24.09.01
 */

@Getter
@Setter
@RequiredAgreementValidation(errorCode = ErrorCode.TERMS_CONSENT_MISSING)
public class RequiredTermsAgreementDto {

    private RequiredTermsAgreePk id; // 복합키

    private String agreeFl; // 'T': 동의, 'F': 비동의

    private int templateSq; //  약관 번호

    /**
     * 동의 여부를 확인하는 메서드입니다.
     *
     * @return 동의 여부 (true: 동의함, false: 비동의함)
     */
    public boolean isAgreed() {
        return "T".equals(agreeFl); // agreeFl이 'T'인 경우 동의함
    }
}
