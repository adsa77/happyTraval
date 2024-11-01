package com.happyTravel.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * 시스템 내 사용자 정보를 나타내는 엔티티 클래스.
 * <p>
 * 데이터베이스의 USER_TB 테이블과 매핑되며, 사용자 로그인 정보, 연락처 정보,
 * 등록/수정 메타데이터 등의 정보를 포함함.
 * </p>
 *
 * @author 손지욱
 * @since 24.09.01
 */
@Entity
@Getter
@NoArgsConstructor  // 매개변수가 없는 기본 생성자를 자동으로 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 생성
@Builder            // 빌더 패턴 사용
@Table(name = "USER_TB")
public class UserColumnEntity {

    /**
     * 사용자 ID (USER_ID 컬럼).
     */
    @Id
    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    /**
     * 사용자 비밀번호 (USER_PWD 컬럼).
     */
    @Column(name = "USER_PWD", length = 60)
    private String userPwd;

    /**
     * 마지막 로그인 일자 (LAST_LOGIN_DT 컬럼).
     */
    @Column(name = "LAST_LOGIN_DT")
    private LocalDate lastLoginDt;

    /**
     * 비밀번호 업데이트 일자 (PWD_UPD_DT 컬럼).
     */
    @Column(name = "PWD_UPD_DT")
    private LocalDate pwdUpdDt;

    /**
     * 이메일 아이디 (EMAIL_ID 컬럼).
     */
    @Column(name = "EMAIL_ID", length = 50)
    private String emailId;

    /**
     * 이메일 주소 (EMAIL_ADDRESS 컬럼).
     */
    @Column(name = "EMAIL_DOMAIN", length = 50)
    private String emailDomain;

    /**
     * 전화번호 (PHONE_NO 컬럼).
     */
    @Column(name = "PHONE_NO", length = 11)
    private String phoneNo;

    /**
     * 주소 (ADDRESS 컬럼).
     */
    @Column(name = "ADDRESS", length = 150)
    private String address;

    /**
     * 등록 및 수정 정보 (RegUpdtInfo 포함).
     */
    @Embedded
    private RegUpdtInfo regUpdtInfo;

}
