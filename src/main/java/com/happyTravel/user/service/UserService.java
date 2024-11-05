package com.happyTravel.user.service;

import com.happyTravel.common.entity.*;
import com.happyTravel.common.entity.OptionalTermsAgreePk;
import com.happyTravel.common.entity.RequiredTermsAgreePk;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.user.dto.OptionalTermsAgreementDto;
import com.happyTravel.user.dto.RequiredTermsAgreementDto;
import com.happyTravel.user.dto.UserLoginDto;
import com.happyTravel.user.dto.UserSignUpDto;
import com.happyTravel.user.repository.OptionalTermsAgreeRepository;
import com.happyTravel.user.repository.RequiredTermsAgreeRepository;
import com.happyTravel.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * UserService 클래스는 회원 가입, 로그인, 사용자 정보 수정 기능을 처리하는 서비스 클래스입니다.
 *
 * <p>회원 가입 시 아이디 중복 체크와 유효성 검사를 수행하며, 사용자 정보를 UserColumnEntity로 변환하여 저장합니다.
 * ObjectMapper 대신 직접 빌더 패턴을 사용하여 DTO를 엔티티로 변환함으로써,
 * 사용자 비밀번호나 전화번호 수정 기능 추가 시 데이터 매핑을 명확하게 처리할 수 있도록 하였습니다.</p>
 *
 * 주요 기능:
 * <ul>
 *   <li>userSignUp: 회원가입 처리 및 검증</li>
 *   <li>validateUserSignUp: 회원가입 시 유효성 검사 및 아이디 중복 체크</li>
 *   <li>userLogin: 로그인 처리 및 사용자 검증</li>
 * </ul>
 *
 * @author 손지욱
 * @since 24.09.01
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RequiredTermsAgreeRepository requiredTermsAgreeRepository;
    private final OptionalTermsAgreeRepository optionalTermsAgreeRepository;


    //  회원가입
    @Transactional
    public CommonResponse userSignUp(UserSignUpDto userSignUpDto) {

        //  유효성 검사
        validateUserSignUp(userSignUpDto);

        //  엔티티로 변환
        //  빌더 패턴
        UserColumnEntity newUser = UserColumnEntity.builder()
                .userId(userSignUpDto.getUserId())
                .userPwd(userSignUpDto.getUserPwd())
                .emailId(userSignUpDto.getEmailId())
                .emailDomain(userSignUpDto.getEmailDomain())
                .phoneNo(userSignUpDto.getPhoneNo())
                .address(userSignUpDto.getAddress())
                .build();

        //  사용자 정보 저장
        userRepository.save(newUser);

        // 필수 약관 동의 저장
        saveRequiredAgreements(userSignUpDto.getRequiredAgreements(), newUser.getUserId());

        // 선택 약관 동의 저장
        saveOptionalAgreements(userSignUpDto.getOptionalAgreements(), newUser.getUserId());

        //  응답 DTO 생성
        CommonResponse response = CommonResponse.builder()
                .message("회원가입이 완료되었습니다.")
                .httpStatus(HttpStatus.CREATED.value()) // HTTP 상태 코드 설정
                .errorCode(null) // 에러 코드 설정 (성공 시 null)
                .build();

        return response;

    }

    // 필수 약관 동의 저장
    private void saveRequiredAgreements(List<RequiredTermsAgreementDto> requiredAgreements, String userId) {
        for (RequiredTermsAgreementDto requiredAgreement : requiredAgreements) {
            if (requiredAgreement.isAgreed()) {
                RequiredTermsAgreePk id = new RequiredTermsAgreePk("U", userId, LocalDateTime.now(), generateSequence(userId));

                // 빌더 패턴을 사용하여 엔티티 생성 (복합키 순서에 맞게 필드를 설정)
                RequiredTermsAgreeEntity agreementEntity = RequiredTermsAgreeEntity.builder()
                        .userType("U") // 유저 타입 설정
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

    // 선택 약관 동의 저장
    private void saveOptionalAgreements(List<OptionalTermsAgreementDto> optionalAgreements, String userId) {
        for(OptionalTermsAgreementDto optionalAgreement : optionalAgreements) {
            if (optionalAgreement.getAgreeFl() != null) { // 'T' 또는 'F' 값이 설정되어 있는지 확인
                OptionalTermsAgreePk id = new OptionalTermsAgreePk("U", userId, LocalDateTime.now(), generateSequence(userId));

                // 빌더 패턴을 사용하여 엔티티 생성 (복합키 순서에 맞게 필드를 설정)
                OptionalTermsAgreeEntity agreementEntity = OptionalTermsAgreeEntity.builder()
                        .userType("U") // 유저 타입 설정
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

    // 시퀀스를 동적으로 생성하는 메소드
    private String generateSequence(String userId) {
        // 현재 사용자의 최대 시퀀스를 조회하는 로직
        int maxSequence = userRepository.findMaxSequenceByUserId(userId);

        // 새로운 시퀀스를 생성합니다. (1 증가)
        int newSequence = maxSequence + 1;

        // 4자리 문자열로 변환하여 반환
        return String.format("%04d", newSequence);

    }


    // 필수 약관 동의가 있는지 확인하는 메소드
    private void validateRequiredAgreements(UserSignUpDto userSignUpDto) {
        if (userSignUpDto.getRequiredAgreements() == null || userSignUpDto.getRequiredAgreements().isEmpty()) {
            throw new CustomException(ErrorCode.TERMS_CONSENT_REQUIRED); // 필수 약관 동의 필수 오류
        }
    }

    //  유효성 검사 및 아이디 중복 체크
    private void validateUserSignUp(UserSignUpDto userSignUpDtoReq) {

        //  아이디 중복 체크
        if (userRepository.findByUserId(userSignUpDtoReq.getUserId()) != null) {
            throw new CustomException(ErrorCode.USER_ID_ALREADY_EXISTS);
        }

        // 필수 약관 동의 확인
        validateRequiredAgreements(userSignUpDtoReq);

    }

    //  로그인
    public CommonResponse userLogin(UserLoginDto userLoginDto) {

        UserColumnEntity loginUserEntity = userRepository.findByUserId(userLoginDto.getUserId());

        // 비밀번호 불일치 확인
        if (!loginUserEntity.getUserPwd().equals(userLoginDto.getUserPwd())) {
            throw new CustomException(ErrorCode.LOGIN_FAILURE); // 비밀번호 불일치
        }

        //  로그인 성공 시 응답 생성
        CommonResponse response = CommonResponse.builder()
                .message("로그인 성공")
                .httpStatus(HttpStatus.OK.value()) // HTTP 상태 코드 설정
                .errorCode(null) // 에러 코드 설정 (성공 시 null)
                .build();

        // 동적으로 데이터 추가
        response.addData("loginSuccess", true);  // 로그인 성공 여부 추가

        return response;

    }

}
