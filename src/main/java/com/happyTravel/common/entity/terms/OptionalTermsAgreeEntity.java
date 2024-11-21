package com.happyTravel.common.entity.terms;

import com.happyTravel.common.entity.pk.OptionalTermsAgreePk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자
@Builder            // 빌더 패턴 사용
@IdClass(OptionalTermsAgreePk.class)
@Table(name = "OPTIONAL_TERMS_AGREE_TH")
public class OptionalTermsAgreeEntity implements Serializable {

    @Id
    @Column(name = "USER_TYPE", length = 1)
    private String userType;

    @Id
    @Column(name = "USER_ID", length = 20)
    private String userId;

    @Id
    @Column(name = "AGREE_DTM", updatable = false)
    private LocalDateTime agreeDtm;

    @Id
    @Column(name = "SEQUENCE", length = 4)
    private String sequence;

    @Column(name = "TEMPLATE_SQ")
    private int templateSq;

    @Column(name = "AGREE_FL", length = 1)
    private String agreeFl;

    @PrePersist
    private void prePersist() {
        if (this.agreeDtm == null) {
            this.agreeDtm = LocalDateTime.now(); // 현재 시간으로 설정
        }
    }

}
