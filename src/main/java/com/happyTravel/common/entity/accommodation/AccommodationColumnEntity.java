package com.happyTravel.common.entity.accommodation;

import com.happyTravel.common.entity.common.RegUpdtInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 숙소 엔티티.
 * <p>
 * 이 클래스는 숙소 정보와 관련된 데이터를 담고 있으며,
 * 해당 엔티티는 데이터베이스의 ACCOMMODATION_TB 테이블과 매핑됩니다.
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
@Table(name = "ACCOMMODATION_TB")
public class AccommodationColumnEntity {

    /**
     * 숙소 ID (ACCOMMODATION_ID).
     */
    @Id
    @Column(name = "ACCOMMODATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accommodationId;

    /**
     * 파트너 ID (USER_ID).
     * PARTNER_TB의 USER_ID와 외래키 관계를 가짐.
     */
    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    /**
     * 숙소 이름 (NAME).
     */
    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    /**
     * 숙소 설명 (DESCRIPTION).
     */
    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    /**
     * 숙소 주소 (ADDRESS).
     */
    @Column(name = "ADDRESS", length = 300, nullable = false)
    private String address;

    /**
     * 숙소 전화번호 (PHONE_NO).
     */
    @Column(name = "PHONE_NO", length = 12, nullable = false)
    private String phoneNo;

    /**
     * 1박당 가격 (PRICE_PER_NIGHT).
     */
    @Column(name = "PRICE_PER_NIGHT")
    private Integer pricePerNight;

    /**
     * 상태 (STATUS). 'A' - 활성, 'I' - 비활성, 'D' - 삭제
     */
    @Column(name = "STATUS", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'I'")
    private String status;  // 기본값은 'I' (비활성)

    /**
     * 최대 수용 인원 (MAX_OCCUPANCY).
     */
    @Column(name = "MAX_OCCUPANCY", nullable = false)
    private Integer maxOccupancy;

    /**
     * 등록 및 수정 정보 (RegUpdtInfo 포함).
     */
    @Embedded
    private RegUpdtInfo regUpdtInfo;

}
