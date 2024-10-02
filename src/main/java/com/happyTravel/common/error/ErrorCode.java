package com.happyTravel.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//  필드를 처리해야 하기 때문에 getter와 constructor가 필요.
@Getter
public enum ErrorCode {

    // 일반 고객의 문제 (1000번대)
    VALIDATION_USER_ID_EMPTY(1000, "ID를 입력하세요."), // ID 입력 오류
    VALIDATION_USER_PASSWORD_EMPTY(1001, "비밀번호를 입력하세요."), // 비밀번호 입력 오류


    // 회원가입 관련 문제 (1100번대)
    USER_ID_ALREADY_EXISTS(1100, "이미 사용 중인 아이디입니다."), // 아이디 중복
    USER_ID_LENGTH(1101, "ID는 4자 이상 20자 이하로 입력하세요."),
    VALIDATION_USER_PASSWORD_REQUIRED(1102, "비밀번호는 필수입니다."), // 비밀번호 필수 오류
    VALIDATION_PASSWORD_MISMATCH(1103, "비밀번호가 일치하지 않습니다."), // 비밀번호 불일치 오류
    VALIDATION_PASSWORD_REQUIREMENTS_NOT_MET(1104, "비밀번호는 숫자, 영문, 특수문자를 포함해야 합니다."), // 비밀번호 규칙 오류
    VALIDATION_PHONE_NUMBER_REQUIRED(1105, "휴대전화번호는 필수입니다."), // 전화번호 필수 오류
    VALIDATION_USER_SIGNUP_DATA(1106, "회원가입 데이터가 유효하지 않습니다."),

    // 로그인 관련 문제 (1200번대)
    INVALID_CREDENTIALS(1200, "잘못된 자격 증명입니다."), // 자격 증명 오류
    LOGIN_FAILURE(1201, "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주시기 바랍니다."), // 로그인 실패 오류

    // 약관 동의 오류 (1300번대)
    TERMS_CONSENT_REQUIRED(1300, "필수 약관에 동의해야 합니다."), // 약관 동의 필수 오류
    TERMS_CONSENT_FAILURE(1301, "약관 동의 등록 실패."), // 약관 동의 등록 실패 오류

    // 파트너의 문제 (2000번대)
    PARTNER_REGISTRATION_FAILURE(2000, "파트너 등록 오류."), // 파트너 등록 오류
    PARTNER_AUTHENTICATION_FAILURE(2001, "파트너 인증 실패."), // 파트너 인증 실패 오류

    // 권한 관련 문제 (4000번대)
    AUTHORITY_NOT_GRANTED(4000, "권한 없음 오류."), // 권한 없음 오류
    AUTHORITY_CHANGE_FAILURE(4001, "권한 변경 실패."), // 권한 변경 실패 오류

    // 관리자 관련 문제 (8000번대)
    ADMIN_ACCOUNT_ERROR(8000, "관리자 계정 오류."), // 관리자 계정 오류
    ADMIN_AUTHORITY_ERROR(8001, "관리자 권한 부족."), // 관리자 권한 부족 오류

    // DB 문제 (9000번대)
    DATABASE_CONNECTION_FAILURE(9000, "데이터베이스 연결 실패."), // 데이터베이스 연결 오류
    DATABASE_QUERY_EXECUTION_ERROR(9001, "쿼리 실행 오류."); // 쿼리 실행 오류


    //  코드 번호, 메시지
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code; // 추가: 에러 코드를 가져오는 메서드
    }

}
