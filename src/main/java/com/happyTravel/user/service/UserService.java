package com.happyTravel.user.service;

import com.happyTravel.common.entity.UserColumnEntity;
import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.user.dto.UserDtoReq;
import com.happyTravel.user.reopsitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    // 회원가입
    public CommonResponse userSignUp(UserDtoReq userDtoReq) {

        CommonResponse response;

        // 아이디 중복 체크
        if (repository.findByUserId(userDtoReq.getSignUpUserId()) != null) {
            response = new CommonResponse(false, "이미 사용 중인 아이디입니다.");
            return response; // 중복 시 바로 응답 반환
        }

        // 엔티티로 변환
        UserColumnEntity newUser = UserColumnEntity.builder()
                .userId(userDtoReq.getSignUpUserId())
                .userPwd(userDtoReq.getSignUpUserPwd())
                .emailId(userDtoReq.getSignUpEmailId())
                .emailAddress(userDtoReq.getSignUpEmailAddress())
                .phoneNo(userDtoReq.getSignUpPhoneNo())
                .build();

        // 사용자 정보 저장
        repository.save(newUser);

        // 응답 DTO 생성
        response = new CommonResponse(true, "회원가입이 완료되었습니다.");
        response.addData("signedUpUserId", newUser.getUserId());  // 필요한 데이터 추가

        return response;

    }

    //로그인
    public CommonResponse userLogin(UserDtoReq userDtoReq) {

        CommonResponse response;

        UserColumnEntity loginUserEntity = repository.findByUserId(userDtoReq.getLoginUserId());

        if (loginUserEntity != null && loginUserEntity.getUserPwd().equals(userDtoReq.getLoginUserPwd())) {
            response = new CommonResponse(true, "로그인 성공");
            response.addData("loginSuccess", true);  // 로그인 성공 여부 추가
            // 추가적인 처리 가능 (예: 사용자 세션 처리 등)
        } else {
            response = new CommonResponse(false, "로그인 실패");
            response.addData("loginSuccess", false);  // 로그인 실패 여부 추가
        }

        return  response;

    }

}
