package com.happyTravel.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor  //매개변수가 없는 기본 생성자를 자동으로 생성
@AllArgsConstructor //모든 필드를 인자로 받는 생성자를 생성
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse {

    private String message;
    private int httpStatus;
    private Integer errorCode;
    private String token;

    @Builder.Default
    private Map<String, Object> data = new HashMap<>();  // 동적으로 데이터를 저장할 곳

//    // 생성자: 성공 여부와 메시지를 기본적으로 처리
//    public CommonResponse(boolean success, String message) {
//        this.message = message;
//        if (success) {
//            this.errorCode = null; // 성공 시 errorCode는 null로 설정
//        }
//    }
//
//    // 에러 응답 생성자
//    public CommonResponse(boolean success, String message, int httpStatus, int errorCode) {
//        this.message = message;
//        this.httpStatus = httpStatus;
//        this.errorCode = errorCode; // 실패 시 errorCode를 설정
//    }

    // 동적으로 데이터를 추가할 메소드
    public void addData(String key, Object value) {
        if (this.data == null) {
            this.data = new HashMap<>();  // data가 null인 경우 초기화
        }
        this.data.put(key, value);
    }

}
