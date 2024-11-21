package com.happyTravel.common.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 등록 및 수정 정보에 대한 엔티티 내 임베디드 클래스.
 * <p>
 * 이 클래스는 등록자, 등록 일시, 수정자, 수정 일시 정보를 포함하며,
 * 데이터베이스의 여러 엔티티에서 공통으로 사용될 수 있는 메타데이터를 관리함.
 * </p>
 *
 * @author 손지욱
 * @since 24.09.01
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RegUpdtInfo {

    /**
     * 등록자 (REG_USER 컬럼).
     */
    @Column(name = "REG_USER", length = 50)
    private String regUser;

    /**
     * 등록 일시 (REG_DTM 컬럼). 기본값은 현재 시간으로 설정됨.
     */
    @Column(name = "REG_DTM")
    @Builder.Default
    private LocalDateTime regDtm = LocalDateTime.now();

    /**
     * 수정자 (UPDT_USER 컬럼).
     */
    @Column(name = "UPDT_USER", length = 50)
    private String updtUser;

    /**
     * 수정 일시 (UPDT_DTM 컬럼).
     */
    @Column(name = "UPDT_DTM")
    private LocalDateTime updtDtm;

}
