package com.happyTravel.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 애플리케이션의 에러 코드를 정의하는 열거형 클래스.
 * <p>
 * 다양한 오류 상황에 대한 코드와 메시지를 포함하며, 특정 상황에 맞는 사용자 오류 메시지를 제공함.
 * </p>
 *
 * <ul>
 *   <li><strong>일반 고객의 문제 (1000번대)</strong></li>
 *   <li><strong>로그인 관련 문제 (1200번대)</strong></li>
 *   <li><strong>약관 동의 오류 (1300번대)</strong></li>
 *   <li><strong>파트너의 문제 (2000번대)</strong></li>
 *   <li><strong>권한 관련 문제 (4000번대)</strong></li>
 *   <li><strong>관리자 관련 문제 (8000번대)</strong></li>
 *   <li><strong>DB 문제 (9000번대)</strong></li>
 * </ul>
 *
 * @author 손지욱
 * @since 24.09.01
 */
@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "서버 내부 오류"),

    // 일반 고객의 문제 (1000번대)
    VALIDATION_USER_ID_EMPTY(1000, "ID를 입력하세요."), // ID 입력이 비어 있을 때 발생하는 오류
    VALIDATION_USER_PASSWORD_EMPTY(1001, "비밀번호를 입력하세요."), // 비밀번호 입력이 비어 있을 때 발생하는 오류
    VALIDATION_ID_PASSWORD_ERROR(1002, "아이디, 비밀번호가 유효하지 않습니다."), // 아이디 또는 비밀번호가 유효하지 않은 경우
    USER_NOT_FOUND(1003, "사용자를 찾을 수 없습니다."),  // 사용자 찾을 수 없는 경우

    // 회원가입 관련 문제 (1100번대)
    VALIDATION_USER_SIGNUP_DATA(1100, "회원가입 데이터가 유효하지 않습니다."), // 회원가입 데이터가 올바르지 않은 경우
    USER_ID_ALREADY_EXISTS(1101, "이미 사용 중인 아이디입니다."), // 아이디 중복 오류
    USER_ID_LENGTH(1102, "ID는 4자 이상 20자 이하로 입력하세요."), // 아이디 길이 규칙 위반
    VALIDATION_USER_PASSWORD_REQUIRED(1103, "비밀번호는 필수입니다."), // 비밀번호가 필수로 요구되는 경우 오류
    VALIDATION_PASSWORD_MISMATCH(1104, "비밀번호가 일치하지 않습니다."), // 비밀번호가 일치하지 않는 경우
    VALIDATION_PASSWORD_REQUIREMENTS_NOT_MET(1105, "비밀번호는 숫자, 영문, 특수문자를 포함해야 합니다."), // 비밀번호 규칙 미충족
    PASSWORD_LENGTH_REQUIREMENTS_NOT_MET(1106, "비밀번호는 최소 8자리 이상이어야 합니다."), // 비밀번호 길이 규칙 위반
    VALIDATION_PHONE_NUMBER_REQUIRED(1107, "휴대전화번호는 필수입니다."), // 휴대전화번호가 필수로 요구되는 경우
    VALIDATION_ADDRESS_REQUIRED(1108, "주소는 필수입니다."), // 주소 입력이 필수인 경우
    VALIDATION_ADDRESS_LENGTH(1109, "주소는 150자 이내로 입력해야 합니다."), // 주소 길이 규칙 위반

    // 로그인 관련 문제 (1200번대)
    VALIDATION_LOGIN_DATA_ERROR(1200, "로그인 데이터가 유효하지 않습니다."), // 로그인 데이터 오류
    INVALID_CREDENTIALS(1201, "잘못된 자격 증명입니다."), // 잘못된 자격 증명 오류
    LOGIN_FAILURE(1202, "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주시기 바랍니다."), // 로그인 실패 시 오류
    INVALID_USER_TYPE(1203, "계정 타입이 맞지 않습니다."),

    // 약관 동의 오류 (1300번대)
    TERMS_CONSENT_REQUIRED(1300, "필수 약관에 동의해야 합니다."), // 필수 약관 동의가 필요할 때 발생하는 오류
    INVALID_AGREEMENT_ID(1301, "잘못된 약관 ID입니다."), // 잘못된 약관 ID 오류
    TERMS_CONSENT_FAILURE(1302, "약관 동의 등록 실패."), // 약관 동의 등록 실패 시 오류
    TERMS_CONSENT_OPTIONAL_FAILURE(1303, "선택 약관 동의 등록 실패."), // 선택 약관 동의 등록 실패 시 오류
    TERMS_CONSENT_OPTIONAL_NOTNULL(1304, "선택 약관을 확인해주세요."), // 선택 약관 미선택 시 오류
    TERMS_CONSENT_MISSING(1305, "필수 약관 동의 정보가 누락되었습니다."), // 필수 약관 동의가 없을 때 발생하는 오류

    // 파트너의 문제 (2000번대)
    PARTNER_REGISTRATION_FAILURE(2000, "파트너 등록 오류."), // 파트너 등록 시 발생하는 오류
    PARTNER_AUTHENTICATION_FAILURE(2001, "파트너 인증 실패."), // 파트너 인증 실패 시 발생하는 오류

    // 권한 관련 문제 (4000번대)
    AUTHORITY_NOT_GRANTED(4000, "권한 없음 오류."), // 권한이 없을 때 발생하는 오류
    AUTHORITY_CHANGE_FAILURE(4001, "권한 변경 실패."), // 권한 변경 실패 시 발생하는 오류

    // 관리자 관련 문제 (8000번대)
    ADMIN_ACCOUNT_ERROR(8000, "관리자 계정 오류."), // 관리자 계정과 관련된 오류
    ADMIN_AUTHORITY_ERROR(8001, "관리자 권한 부족."), // 관리자 권한 부족 시 발생하는 오류

    // DB 문제 (9000번대)
    DATABASE_CONNECTION_FAILURE(9000, "데이터베이스 연결 실패."), // 데이터베이스 연결 실패 시 발생하는 오류
    DATABASE_QUERY_EXECUTION_ERROR(9001, "쿼리 실행 오류."); // 쿼리 실행 실패 시 발생하는 오류

    // 코드 번호와 메시지 정의
    private final int code; // 오류 코드
    private final String message; // 오류 메시지

    /**
     * ErrorCode 열거형 생성자.
     *
     * @param code 오류 코드
     * @param message 오류 메시지
     */
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 오류 메시지를 가져오는 메서드.
     *
     * @return 오류 메시지
     */
    public String getMessage() {
        return message;
    }

    /**
     * 오류 코드를 가져오는 메서드.
     *
     * @return 오류 코드
     */
    public int getCode() {
        return code;
    }
}
