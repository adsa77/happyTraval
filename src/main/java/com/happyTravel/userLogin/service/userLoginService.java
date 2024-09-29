package com.happyTravel.userLogin.service;

import com.happyTravel.userLogin.entity.UserLoginEntity;
import com.happyTravel.userLogin.model.UserLoginDtoReq;
import com.happyTravel.userLogin.model.UserLoginDtoResp;
import com.happyTravel.userLogin.repository.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserLoginRepository repository;

    public UserLoginDtoResp login(UserLoginDtoReq loginDtoReq) {
        UserLoginDtoResp response = new UserLoginDtoResp();
        UserLoginEntity loginEntity = repository.findByUserId(loginDtoReq.getUserId());

        if (loginEntity != null && loginEntity.getUserPwd().equals(loginDtoReq.getUserPwd())) {
            response.setSuccess(true);
            response.setMessage("로그인 성공");
            // 여기에 추가적인 처리 (마지막 로그인 시간 업데이트 등)를 넣을 수 있습니다.
        } else {
            response.setSuccess(false);
            response.setMessage("로그인 실패");
        }
        return response;
    }
}
