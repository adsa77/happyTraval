package com.happyTravel.common.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserTypeEntity는 사용자 유형을 정의하는 엔티티 클래스입니다.
 * 이 클래스는 데이터베이스의 USER_TYPE_TC 테이블과 매핑됩니다.
 *
 * @author 손지욱
 * @since 24.09.01
 */

@Entity
@Getter
@NoArgsConstructor  // 매개변수가 없는 기본 생성자를 자동으로 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 생성
@Builder            // 빌더 패턴 사용
@Table(name = "USER_TYPE_TC")
public class UserTypeEntity {

    @Id // 기본 키를 지정
    @Column(name = "USER_TYPE", length = 1) // USER_TYPE 열에 매핑, 길이는 1
    private String userType; // 사용자 유형을 나타내는 필드, 값: 'P'(파트너), 'U'(회원), 'A'(관리자)

}
