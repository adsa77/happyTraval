package com.happyTravel.common.exception;

import com.happyTravel.common.response.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse> handleCustomException(CustomException cEx) {

        // 에러 코드와 메시지를 MDC에 추가
        MDC.put("errorCode", String.valueOf(cEx.getErrorCodeNumber()));
        MDC.put("errorMessage", cEx.getErrorCode().getMessage());

        //  빌더 패턴
        CommonResponse response = CommonResponse.builder()
                .message(cEx.getErrorCode().getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST.value()) // HTTP 상태 코드 설정
                .errorCode(cEx.getErrorCodeNumber())        // 최상위 에러 코드 설정
                .build();

        // 요청 오류에 대한 응답
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CommonResponse> handleException(Exception ex) {
//        CommonResponse response = CommonResponse.builder()
//                .message("서버에서 오류가 발생했습니다. 잠시 후 다시 시도해주세요.") // 일반적인 메시지
//                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .build();
//
//        // 서버 오류에 대한 응답
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
