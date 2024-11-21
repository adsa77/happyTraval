package com.happyTravel.user.controller;

import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.user.dto.UserLoginDto;
import com.happyTravel.user.dto.UserSignUpDto;
import com.happyTravel.user.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이용자 관련 API를 처리하는 REST 컨트롤러.
 * <p>
 * 사용자 회원가입 및 로그인 기능을 제공하며, 요청에 대한 적절한 응답을 반환한다.
 * </p>
 *
 * @author 손지욱
 * @since 24.09.01
 */

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserAccountRestController {

    private final UserAccountService service;

    /**
     * 사용자 회원가입을 처리하는 메서드.
     *
     * @param userSignUpDto 사용자 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과를 담은 응답 객체
     */
    @PostMapping("signUp")
    public ResponseEntity<CommonResponse> userSingUp(@Valid @RequestBody UserSignUpDto userSignUpDto){
        CommonResponse response = service.userSignUp(userSignUpDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 사용자 로그인을 처리하는 메서드.
     *
     * @param userLoginDto 사용자 로그인 요청 데이터
     * @return ResponseEntity<CommonResponse> 로그인 결과를 담은 응답 객체
     */
    @PostMapping("login")
    public ResponseEntity<CommonResponse> userlogin(@Valid @RequestBody UserLoginDto userLoginDto){
        CommonResponse response = service.userLogin(userLoginDto);
        return ResponseEntity.ok(response);
    }


}
