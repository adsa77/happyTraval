package com.happyTravel.user.service;

import com.happyTravel.common.entity.UserColumnEntity;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.common.exception.CustomException;
import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.user.dto.UserLoginDtoReq;
import com.happyTravel.user.dto.UserSignUpDtoReq;
import com.happyTravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
                .address(userSignUpDtoReq.getAddress())
                .build();

        // 사용자 정보 저장
        repository.save(newUser);

        // 응답 DTO 생성
        CommonResponse response = new CommonResponse(true, "회원가입이 완료되었습니다.");
        response.addData("signedUpUserId", newUser.getUserId());
        response.setHttpStatus(HttpStatus.CREATED.value()); // HTTP 상태 코드 설정

        return response;

    }

    // 유효성 검사 및 아이디 중복 체크
    private void validateUserSignUp(UserSignUpDtoReq userSignUpDtoReq) {

        // 아이디 중복 체크
        if (repository.findByUserId(userSignUpDtoReq.getUserId()) != null) {
            throw new CustomException(ErrorCode.USER_ID_ALREADY_EXISTS);
        }
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
        response.setHttpStatus(HttpStatus.OK.value()); // HTTP 상태 코드 설정

        return response;

    }

}
