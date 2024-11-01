//package com.happyTravel.admin.controller;
//
//import com.happyTravel.admin.dto.AdminLoginDto;
//import com.happyTravel.admin.dto.AdminSingUpDto;
//import com.happyTravel.admin.service.AdminService;
//import com.happyTravel.common.response.CommonResponse;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("api/admin")
//@RequiredArgsConstructor
//public class AdminRestController {
//
//    private final AdminService service;
//
//    @PostMapping("signUp")
//    public ResponseEntity<CommonResponse> adminSignUp(@Valid @RequestBody AdminSingUpDto adminSingUpDto){
//        CommonResponse response = service.adminSignUp(adminSingUpDto);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    @PostMapping("login")
//    public ResponseEntity<CommonResponse> adminLogin(@Valid @RequestBody AdminLoginDto adminLoginDto){
//        CommonResponse response = service.adminLogin(adminLoginDto);
//        return ResponseEntity.ok(response);
//    }
//
//}
