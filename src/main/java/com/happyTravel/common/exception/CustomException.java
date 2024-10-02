package com.happyTravel.common.exception;

import com.happyTravel.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // 에러 코드를 반환하는 메소드 추가
    public int getErrorCodeNumber() {
        return errorCode.getCode();
    }

}
