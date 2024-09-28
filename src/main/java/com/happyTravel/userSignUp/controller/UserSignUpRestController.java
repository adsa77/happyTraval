package com.happyTravel.userSignUp.controller;

import com.happyTravel.userSignUp.model.UserSignUpDtoReq;
import com.happyTravel.userSignUp.model.UserSignUpDtoResp;
import com.happyTravel.userSignUp.service.UserSignUpService;
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
public class UserSignUpRestController {

    private final UserSignUpService service;

    @PostMapping("signUp")
    public ResponseEntity<UserSignUpDtoResp> userSingUp(@Valid @RequestBody UserSignUpDtoReq userSingUpDtoReq){
        UserSignUpDtoResp response = service.signUp(userSingUpDtoReq);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
