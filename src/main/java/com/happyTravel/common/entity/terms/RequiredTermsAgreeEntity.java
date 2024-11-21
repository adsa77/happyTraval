package com.happyTravel.common.entity.terms;

import com.happyTravel.common.entity.pk.RequiredTermsAgreePk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor  //매개변수가 없는 기본 생성자를 자동으로 생성
@AllArgsConstructor //모든 필드를 인자로 받는 생성자를 생성
@Builder            //빌더 패턴 사용
@IdClass(RequiredTermsAgreePk.class)
@Table(name = "REQUIRED_TERMS_AGREE_TH")
public class RequiredTermsAgreeEntity implements Serializable {

    @Id
    @Column(name = "USER_TYPE", length = 1)
    private String userType;

    @Id
    @Column(name = "USER_ID", length = 20)
    private String userId;

    @Id
    @Column(name = "AGREE_DTM", updatable = false)
    private LocalDateTime agreeDtm; // 복합 키의 일부분

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
