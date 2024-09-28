package com.happyTravel.userSignUp.service;

import com.happyTravel.userSignUp.entity.UserSignUpEntity;
import com.happyTravel.userSignUp.model.UserSignUpDtoReq;
import com.happyTravel.userSignUp.model.UserSignUpDtoResp;
import com.happyTravel.userSignUp.repository.UserSignUpRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignUpService {

    private final UserSignUpRepository repository;

    @Transactional
    public UserSignUpDtoResp signUp(UserSignUpDtoReq userSingUpDtoReq){
        // 엔티티로 변환
        UserSignUpEntity newUser = new UserSignUpEntity();
        newUser.setUserId(userSingUpDtoReq.getUserId());
        newUser.setUserPwd(userSingUpDtoReq.getUserPwd());
        newUser.setEmailId(userSingUpDtoReq.getEmailId());
        newUser.setEmailAddress(userSingUpDtoReq.getEmailAddress());
        newUser.setPhoneNo(userSingUpDtoReq.getPhoneNo());
        newUser.setRegUser(userSingUpDtoReq.getRegUser());

        // 사용자 정보 저장
        repository.save(newUser);

        // 응답 DTO 생성
        UserSignUpDtoResp response = new UserSignUpDtoResp();
        response.setMessage("회원가입이 완료되었습니다.");
        response.setUserId(newUser.getUserId());

        return response;
    }

}
