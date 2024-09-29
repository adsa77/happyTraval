package com.happyTravel.userLogin.controller;

import com.happyTravel.userLogin.model.UserLoginDtoReq;
import com.happyTravel.userLogin.model.UserLoginDtoResp;
import com.happyTravel.userLogin.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserLoginRestController {

    private final UserLoginService service;

    @PostMapping("/login")
    public ResponseEntity<UserLoginDtoResp> login(@RequestBody UserLoginDtoReq loginDtoReq) {
        UserLoginDtoResp response = service.login(loginDtoReq);
        return ResponseEntity.ok(response);
    }

}
