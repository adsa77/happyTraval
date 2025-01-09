package com.happyTravel.partner.service;

import com.happyTravel.common.entity.partner.PartnerColumnEntity;
import com.happyTravel.common.entity.pk.OptionalTermsAgreePk;
import com.happyTravel.common.entity.pk.RequiredTermsAgreePk;
import com.happyTravel.common.entity.terms.OptionalTermsAgreeEntity;
import com.happyTravel.common.entity.terms.RequiredTermsAgreeEntity;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.partner.dto.PartnerSignUpDto;
import com.happyTravel.partner.repository.PartnerAccountRepository;
import com.happyTravel.user.dto.OptionalTermsAgreementDto;
import com.happyTravel.user.dto.RequiredTermsAgreementDto;
import com.happyTravel.user.repository.OptionalTermsAgreeRepository;
import com.happyTravel.user.repository.RequiredTermsAgreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerAccountService {

    private final PartnerAccountRepository partnerRepository;
    private final RequiredTermsAgreeRepository requiredTermsAgreeRepository;
    private final OptionalTermsAgreeRepository optionalTermsAgreeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CommonResponse partnerSignUp(PartnerSignUpDto partnerSignUpDto) {

        //  유효성 검사
        validateUserSignUp(partnerSignUpDto);

        //  비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(partnerSignUpDto.getUserPwd());

        //  엔티티로 변환
        //  빌더 패턴
        PartnerColumnEntity newUser = PartnerColumnEntity.builder()
                .userId(partnerSignUpDto.getUserId())
                .userPwd(encryptedPassword)
                .emailId(partnerSignUpDto.getEmailId())
                .emailDomain(partnerSignUpDto.getEmailDomain())
                .landLinePhoneNo(partnerSignUpDto.getLandLinePhoneNo())
                .businessLicenseNumber(partnerSignUpDto.getBusinessLicenseNumber())
                .phoneNo(partnerSignUpDto.getPhoneNo())
                .address(partnerSignUpDto.getAddress())
                .build();

        //  사용자 정보 저장
        partnerRepository.save(newUser);

        // 필수 약관 동의 저장
        saveRequiredAgreements(partnerSignUpDto.getRequiredAgreements(), newUser.getUserId());

        // 선택 약관 동의 저장
        saveOptionalAgreements(partnerSignUpDto.getOptionalAgreements(), newUser.getUserId());

        //  응답 DTO 생성
        CommonResponse response = CommonResponse.builder()
                .message("회원가입이 완료되었습니다.")
                .httpStatus(HttpStatus.CREATED.value()) // HTTP 상태 코드 설정
                .errorCode(null) // 에러 코드 설정 (성공 시 null)
                .build();

        return response;
    }

    private void saveRequiredAgreements(List<RequiredTermsAgreementDto> requiredAgreements, String userId) {
        for (RequiredTermsAgreementDto requiredAgreement : requiredAgreements) {
            if (requiredAgreement.isAgreed()) {
                RequiredTermsAgreePk id = new RequiredTermsAgreePk("P", userId, LocalDateTime.now(), generateSequence(userId));

                // 빌더 패턴을 사용하여 엔티티 생성 (복합키 순서에 맞게 필드를 설정)
                RequiredTermsAgreeEntity agreementEntity = RequiredTermsAgreeEntity.builder()
                        .userType("P") // 유저 타입 설정
                        .userId(userId)
                        .agreeDtm(id.getAgreeDtm()) // agreeDtm 설정 (현재 시간으로 설정)
                        .sequence(id.getSequence()) // 시퀀스 설정
                        .agreeFl(requiredAgreement.getAgreeFl()) // 동의 여부 설정
                        .templateSq(requiredAgreement.getTemplateSq()) // 약관 번호
                        .build();

                requiredTermsAgreeRepository.save(agreementEntity); // DB에 동의 정보 저장
            }
        }
    }

    private void saveOptionalAgreements(List<OptionalTermsAgreementDto> optionalAgreements, String userId) {
        for(OptionalTermsAgreementDto optionalAgreement : optionalAgreements) {
            if (optionalAgreement.getAgreeFl() != null) { // 'T' 또는 'F' 값이 설정되어 있는지 확인
                OptionalTermsAgreePk id = new OptionalTermsAgreePk("P", userId, LocalDateTime.now(), generateSequence(userId));

                // 빌더 패턴을 사용하여 엔티티 생성 (복합키 순서에 맞게 필드를 설정)
                OptionalTermsAgreeEntity agreementEntity = OptionalTermsAgreeEntity.builder()
                        .userType("P") // 유저 타입 설정
                        .userId(userId)
                        .agreeDtm(id.getAgreeDtm()) // agreeDtm 설정 (현재 시간으로 설정)
                        .sequence(id.getSequence()) // 시퀀스 설정
                        .agreeFl(optionalAgreement.getAgreeFl()) // 선택 약관 동의 여부 설정 ('T' 또는 'F')
                        .templateSq(optionalAgreement.getTemplateSq()) // 약관 번호
                        .build();

                optionalTermsAgreeRepository.save(agreementEntity);
            }
        }
    }

    private String generateSequence(String userId) {
        // 현재 사용자의 최대 시퀀스를 조회하는 로직
        int maxSequence = partnerRepository.findMaxSequenceByUserId(userId);

        // 새로운 시퀀스를 생성합니다. (1 증가)
        int newSequence = maxSequence + 1;

        // 4자리 문자열로 변환하여 반환
        return String.format("%04d", newSequence);

    }

    private void validateRequiredAgreements(PartnerSignUpDto partnerSignUpDto) {
        if (partnerSignUpDto.getRequiredAgreements() == null || partnerSignUpDto.getRequiredAgreements().isEmpty()) {
            throw new CustomException(ErrorCode.TERMS_CONSENT_REQUIRED); // 필수 약관 동의 필수 오류
        }
    }

    private void validateUserSignUp(PartnerSignUpDto partnerSignUpDto) {

        //  아이디 중복 체크
        if (partnerRepository.findByUserId(partnerSignUpDto.getUserId()) != null) {
            throw new CustomException(ErrorCode.USER_ID_ALREADY_EXISTS);
        }

        // 필수 약관 동의 확인
        validateRequiredAgreements(partnerSignUpDto);

    }


}
