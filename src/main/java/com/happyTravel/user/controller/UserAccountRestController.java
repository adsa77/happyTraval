package com.happyTravel.user.controller;

import com.happyTravel.common.response.CommonResponse;
import com.happyTravel.security.jwt.JwtTokenProvider;
import com.happyTravel.user.dto.UserLoginDto;
import com.happyTravel.user.dto.UserSignUpDto;
import com.happyTravel.user.service.UserAccountService;
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

//    /**
//     * 사용자 로그인을 처리하는 메서드.
//     *
//     * @param userLoginDto 사용자 로그인 요청 데이터
//     * @return ResponseEntity<CommonResponse> 로그인 결과를 담은 응답 객체
//     */
//    @PostMapping("login")
//    public ResponseEntity<CommonResponse> userlogin(@Valid @RequestBody UserLoginDto userLoginDto){
//        CommonResponse response = service.userLogin(userLoginDto);
//        return ResponseEntity.ok(response);
//    }


//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;  // JwtTokenProvider 필드 선언 및 주입
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
//        // 사용자 인증 로직 (예시로 간단한 인증을 구현)
//        // 실제 인증 로직은 UserDetailsService 등을 사용하여 인증해야 합니다.
//
//        String username = userLoginDto.getUserId();
//        String password = userLoginDto.getUserPwd();
//
//        // 예시로 사용자가 인증된다고 가정
//        // 실제로는 유효한 사용자 인증 후 JWT 토큰을 생성해야 합니다.
//
//        String token = jwtTokenProvider.createAccessToken(username);  // JWT 토큰 생성
//        String refreshToken = jwtTokenProvider.createRefreshToken(username);  // Refresh 토큰
//
//        // CommonResponse 객체 생성
//        CommonResponse response = CommonResponse.builder()
//                .message("로그인 성공")
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
