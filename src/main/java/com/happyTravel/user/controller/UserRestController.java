package com.happyTravel.user.controller;

import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.user.dto.UserDtoReq;
import com.happyTravel.user.dto.UserLoginDtoReq;
import com.happyTravel.user.dto.UserSignUpDtoReq;
import com.happyTravel.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService service;

    @PostMapping("signUp")
    public ResponseEntity<CommonResponse> userSingUp(@Valid @RequestBody UserSignUpDtoReq userSignUpDtoReq){
        CommonResponse response = service.userSignUp(userSignUpDtoReq);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<CommonResponse> userlogin(@RequestBody UserLoginDtoReq userLoginDtoReq){
        CommonResponse response = service.userLogin(userLoginDtoReq);
        return ResponseEntity.ok(response);
    }


}
