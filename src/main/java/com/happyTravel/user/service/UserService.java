package com.happyTravel.user.service;

import com.happyTravel.common.entity.UserColumnEntity;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.user.dto.UserLoginDtoReq;
import com.happyTravel.user.dto.UserSignUpDtoReq;
import com.happyTravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    // 회원가입
    public CommonResponse userSignUp(UserSignUpDtoReq userSignUpDtoReq) {

        // 유효성 검사
        validateUserSignUp(userSignUpDtoReq);

        // 엔티티로 변환
        UserColumnEntity newUser = UserColumnEntity.builder()
                .userId(userSignUpDtoReq.getUserId())
                .userPwd(userSignUpDtoReq.getUserPwd())
                .emailId(userSignUpDtoReq.getEmailId())
                .emailAddress(userSignUpDtoReq.getEmailAddress())
                .phoneNo(userSignUpDtoReq.getPhoneNo())
                .build();

        // 사용자 정보 저장
        repository.save(newUser);

        // 응답 DTO 생성
        CommonResponse response = new CommonResponse(true, "회원가입이 완료되었습니다.");
        response.addData("signedUpUserId", newUser.getUserId());

        return response;

    }

    // 유효성 검사 및 아이디 중복 체크
    private void validateUserSignUp(UserSignUpDtoReq userSignUpDtoReq) {
        // ID 유효성 검사
        if (userSignUpDtoReq.getUserId() == null || userSignUpDtoReq.getUserId().isEmpty()) {
            throw new CustomException(ErrorCode.VALIDATION_USER_ID_EMPTY);
        }

        // 아이디 중복 체크
        if (repository.findByUserId(userSignUpDtoReq.getUserId()) != null) {
            throw new CustomException(ErrorCode.USER_ID_ALREADY_EXISTS);
        }

        // 비밀번호 유효성 검사
        if (userSignUpDtoReq.getUserPwd() == null || userSignUpDtoReq.getUserPwd().isBlank()) {
            throw new CustomException(ErrorCode.VALIDATION_USER_PASSWORD_REQUIRED);
        }

        // 비밀번호 규칙 검사
        if (!userSignUpDtoReq.getUserPwd().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$")) {
            throw new CustomException(ErrorCode.VALIDATION_PASSWORD_REQUIREMENTS_NOT_MET);
        }

        // 전화번호 필수 검사
        if (userSignUpDtoReq.getPhoneNo() == null || userSignUpDtoReq.getPhoneNo().isEmpty()) {
            throw new CustomException(ErrorCode.VALIDATION_PHONE_NUMBER_REQUIRED);
        }

        // 비밀번호 확인 검사
        // (이 부분은 필요시 추가: 예: 사용자가 비밀번호 확인 입력을 받았다면)
        // if (!userSignUpDtoReq.getUserPwd().equals(userSignUpDtoReq.getConfirmPassword())) {
        //     throw new CustomException(ErrorCode.VALIDATION_PASSWORD_MISMATCH);
        // }
    }

    //로그인
    public CommonResponse userLogin(UserLoginDtoReq userLoginDtoReq) {

        UserColumnEntity loginUserEntity = repository.findByUserId(userLoginDtoReq.getUserId());

        // 사용자 ID가 존재하지 않거나 비밀번호가 일치하지 않는 경우 예외 던지기
        if (loginUserEntity == null) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS); // 사용자 ID가 없음
        } else if (!loginUserEntity.getUserPwd().equals(userLoginDtoReq.getUserPwd())) {
            throw new CustomException(ErrorCode.LOGIN_FAILURE); // 비밀번호 불일치
        }

        // 로그인 성공 시 응답 생성
        CommonResponse response = new CommonResponse(true, "로그인 성공");
        response.addData("loginSuccess", true);  // 로그인 성공 여부 추가

        return response;

    }

}
