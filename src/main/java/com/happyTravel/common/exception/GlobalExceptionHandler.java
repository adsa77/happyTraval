package com.happyTravel.common.exception;

import com.happyTravel.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse> handleCustomException(CustomException cEx) {
        // CustomException에서 에러 코드와 메시지를 가져와서 CommonResponse 생성
        CommonResponse response = new CommonResponse(false, cEx.getErrorCode().getMessage());
        response.setHttpStatus(HttpStatus.BAD_REQUEST.value()); // HTTP 상태 코드 설정
        response.setErrorCode(cEx.getErrorCodeNumber()); // 최상위 에러 코드 설정

        // 요청 오류에 대한 응답
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
