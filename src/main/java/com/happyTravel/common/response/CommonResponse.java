package com.happyTravel.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {

    private boolean success;
    private String message;
    private int httpStatus;
    private Integer errorCode;
    private Map<String, Object> data = new HashMap<>();  // 동적으로 데이터를 저장할 곳

    // 생성자: 성공 여부와 메시지를 기본적으로 처리
    public CommonResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        if (success) {
            this.errorCode = null; // 성공 시 errorCode는 null로 설정
        }
    }

    // 에러 응답 생성자
    public CommonResponse(boolean success, String message, int httpStatus, int errorCode) {
        this.success = success;
        this.message = message;
        this.httpStatus = httpStatus;
        this.errorCode = errorCode; // 실패 시 errorCode를 설정
    }

    // 동적으로 데이터를 추가할 메소드
    public void addData(String key, Object value) {
        this.data.put(key, value);
    }

}
