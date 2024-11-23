package com.happyTravel.user.service;

import com.happyTravel.common.entity.pk.OptionalTermsAgreePk;
import com.happyTravel.common.entity.pk.RequiredTermsAgreePk;
import com.happyTravel.common.entity.terms.OptionalTermsAgreeEntity;
import com.happyTravel.common.entity.terms.RequiredTermsAgreeEntity;
import com.happyTravel.common.entity.user.UserColumnEntity;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.security.jwt.JwtTokenProvider;
import com.happyTravel.user.dto.OptionalTermsAgreementDto;
import com.happyTravel.user.dto.RequiredTermsAgreementDto;
import com.happyTravel.user.dto.UserLoginDto;
import com.happyTravel.user.dto.UserSignUpDto;
import com.happyTravel.user.repository.OptionalTermsAgreeRepository;
import com.happyTravel.user.repository.RequiredTermsAgreeRepository;
import com.happyTravel.user.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserAccountService {

    private final UserAccountRepository userRepository;
    private final RequiredTermsAgreeRepository requiredTermsAgreeRepository;
    private final OptionalTermsAgreeRepository optionalTermsAgreeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입을 처리하는 메서드입니다.
     *
     * <p>회원 정보의 유효성을 검증한 후, 유효한 사용자 정보를 DB에 저장하고
     * 필수 및 선택 약관 동의를 처리하여 응답을 생성합니다.</p>
     *
     * @param userSignUpDto 사용자 회원가입 정보를 담고 있는 DTO
     * @return 회원가입 처리 결과를 담은 CommonResponse 객체
     */
    @Transactional
    public CommonResponse userSignUp(UserSignUpDto userSignUpDto) {

        //  유효성 검사
        validateUserSignUp(userSignUpDto);

        //  비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(userSignUpDto.getUserPwd());

        //  엔티티로 변환
        //  빌더 패턴
        UserColumnEntity newUser = UserColumnEntity.builder()
                .userId(userSignUpDto.getUserId())
                .userPwd(encryptedPassword)
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

    /**
     * 필수 약관 동의 정보를 DB에 저장하는 메서드입니다.
     *
     * @param requiredAgreements 필수 약관 동의 목록
     * @param userId 사용자 ID
     */
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

    /**
     * 선택 약관 동의 정보를 DB에 저장하는 메서드입니다.
     *
     * @param optionalAgreements 선택 약관 동의 목록
     * @param userId 사용자 ID
     */
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

    /**
     * 사용자별 새로운 시퀀스를 생성하는 메서드입니다.
     *
     * <p>현재 사용자의 최대 시퀀스를 조회하여 1을 증가시킨 후 반환합니다.</p>
     *
     * @param userId 사용자 ID
     * @return 새로 생성된 시퀀스 값
     */
    private String generateSequence(String userId) {
        // 현재 사용자의 최대 시퀀스를 조회하는 로직
        int maxSequence = userRepository.findMaxSequenceByUserId(userId);

        // 새로운 시퀀스를 생성합니다. (1 증가)
        int newSequence = maxSequence + 1;

        // 4자리 문자열로 변환하여 반환
        return String.format("%04d", newSequence);

    }

    /**
     * 필수 약관 동의가 있는지 확인하는 메서드입니다.
     *
     * @param userSignUpDto 사용자 회원가입 정보 DTO
     * @throws CustomException 필수 약관 동의가 누락된 경우 예외를 발생시킵니다.
     */
    private void validateRequiredAgreements(UserSignUpDto userSignUpDto) {
        if (userSignUpDto.getRequiredAgreements() == null || userSignUpDto.getRequiredAgreements().isEmpty()) {
            throw new CustomException(ErrorCode.TERMS_CONSENT_REQUIRED); // 필수 약관 동의 필수 오류
        }
    }

    /**
     * 회원가입 시 유효성 검사 및 아이디 중복을 체크하는 메서드입니다.
     *
     * @param userSignUpDtoReq 사용자 회원가입 정보 DTO
     * @throws CustomException 중복된 아이디 또는 필수 약관 동의가 누락된 경우 예외를 발생시킵니다.
     */
    private void validateUserSignUp(UserSignUpDto userSignUpDtoReq) {

        //  아이디 중복 체크
        if (userRepository.findByUserId(userSignUpDtoReq.getUserId()) != null) {
            throw new CustomException(ErrorCode.USER_ID_ALREADY_EXISTS);
        }

        // 필수 약관 동의 확인
        validateRequiredAgreements(userSignUpDtoReq);

    }

    /**
     * 로그인 처리를 수행하는 메서드입니다.
     *
     * @param userLoginDto 사용자 로그인 정보 DTO
     * @return 로그인 처리 결과를 담은 CommonResponse 객체
     * @throws CustomException 비밀번호가 일치하지 않는 경우 예외를 발생시킵니다.
     */
    public CommonResponse userLogin(UserLoginDto userLoginDto) {

        // 사용자 정보 조회
        UserColumnEntity loginUserEntity = userRepository.findByUserId(userLoginDto.getUserId());

//        // 비밀번호 불일치 확인
//        if (!loginUserEntity.getUserPwd().equals(userLoginDto.getUserPwd())) {
//            throw new CustomException(ErrorCode.LOGIN_FAILURE); // 비밀번호 불일치
//        }

        // 사용자가 존재하지 않거나 비밀번호가 일치하지 않는 경우 처리
        if (loginUserEntity == null || !passwordEncoder.matches(userLoginDto.getUserPwd(), loginUserEntity.getUserPwd())) {
            throw new CustomException(ErrorCode.LOGIN_FAILURE); // 비밀번호 불일치 또는 사용자 없음
        }

        // 로그인 성공 시 JWT 생성
        String token = jwtTokenProvider.createToken(loginUserEntity.getUserId());

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
