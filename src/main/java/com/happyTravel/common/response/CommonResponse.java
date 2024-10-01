package com.happyTravel.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CommonResponse {

    private boolean success;
    private String message;
    private Map<String, Object> data = new HashMap<>();  // 동적으로 데이터를 저장할 곳

    // 생성자: 성공 여부와 메시지를 기본적으로 처리
    public CommonResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // 동적으로 데이터를 추가할 메소드
    public void addData(String key, Object value) {
        this.data.put(key, value);  // key는 "loginSuccess" 등, value는 해당 값
    }

}
