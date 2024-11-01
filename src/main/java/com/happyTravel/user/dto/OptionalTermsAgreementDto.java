package com.happyTravel.user.dto;

import com.happyTravel.common.entity.OptionalTermsAgreePk;
import lombok.Getter;
import lombok.Setter;

/**
 * OptionalTermsAgreementDto는 선택 약관 동의 정보를 담는 DTO입니다.
 *
 * @author 손지욱
 * @since 24.09.01
 */

@Getter
@Setter

public class OptionalTermsAgreementDto {

    private OptionalTermsAgreePk id; // 복합키

    private String agreeFl; // 'T': 동의, 'F': 비동의

    private int templateSq; //  약관 번호

}
