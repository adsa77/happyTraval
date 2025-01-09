package com.happyTravel.partner.controller;

import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.partner.dto.PartnerSignUpDto;
import com.happyTravel.partner.service.PartnerAccountService;
import com.happyTravel.security.jwt.JwtTokenProvider;
import com.happyTravel.user.dto.UserLoginDto;
import com.happyTravel.user.dto.UserSignUpDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/partner")
@RequiredArgsConstructor
public class PartnerAccountRestController {

    private final PartnerAccountService service;

    @PostMapping("signUp")
    public ResponseEntity<CommonResponse> partnerSignUp(@Valid @RequestBody PartnerSignUpDto partnerSignUpDto){
        CommonResponse response = service.partnerSignUp(partnerSignUpDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;  // JwtTokenProvider 필드 선언 및 주입
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody PartnerSignUpDto partnerSignUpDto) {
//        // 사용자 인증 로직 (예시로 간단한 인증을 구현)
//        // 실제 인증 로직은 UserDetailsService 등을 사용하여 인증해야 합니다.
//
//        String username = partnerSignUpDto.getUserId();
//        String password = partnerSignUpDto.getUserPwd();
//
//        // 예시로 사용자가 인증된다고 가정
//        // 실제로는 유효한 사용자 인증 후 JWT 토큰을 생성해야 합니다.
//
//        String token = jwtTokenProvider.createAccessToken(username);  // JWT 토큰 생성
//        String refreshToken = jwtTokenProvider.createRefreshToken(username);  // Refresh 토큰
//
//        // CommonResponse 객체 생성
//        CommonResponse response = CommonResponse.builder()
//                .message("로그인 성공c")
//                .httpStatus(HttpServletResponse.SC_OK)  // HTTP 200 OK
//                .token(token)
//                .build();
//
//        // 응답에 추가 데이터 넣기
//        response.addData("accessToken", token);  // accessToken 추가
//        response.addData("refreshToken", refreshToken);  // refreshToken 추가
//
//        // ResponseEntity로 반환 (200 OK와 함께 응답 반환)
//        return ResponseEntity.ok(response);
//    }


}
