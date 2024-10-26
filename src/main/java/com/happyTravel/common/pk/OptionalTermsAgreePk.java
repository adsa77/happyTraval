package com.happyTravel.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Embeddable
@Getter
public class OptionalTermsAgreePk implements Serializable {

    @Column(name = "USER_TYPE", length = 1)
    private String userType;

    @Column(name = "USER_ID", length = 50)
    private String userId;

    @Column(name = "AGREE_DTM")
    private LocalDateTime agreeDtm;

    @Column(name = "SEQUENCE", length = 4)
    private String sequence;

    public OptionalTermsAgreePk() {}

    public OptionalTermsAgreePk(String userType, String userId, LocalDateTime agreeDtm, String sequence) {
        this.userType = userType;
        this.userId = userId;
        this.agreeDtm = agreeDtm;
        this.sequence = sequence;
    }

    // equals()와 hashCode() 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OptionalTermsAgreePk)) return false;
        OptionalTermsAgreePk that = (OptionalTermsAgreePk) o;
        return userType.equals(that.userType) &&
                userId.equals(that.userId) &&
                agreeDtm.equals(that.agreeDtm) &&
                sequence.equals(that.sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userType, userId, agreeDtm, sequence);
    }

    @PrePersist
    protected void onCreate() {
        // 현재 시간을 agreeDtm 필드에 저장
        if (agreeDtm == null) {
            agreeDtm = LocalDateTime.now();
        }
    }

    // agreeDtm을 yyyyMMddHHmmss 형식의 문자열로 변환하는 메서드
    public String getFormattedAgreeDtm() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return agreeDtm.format(formatter);
    }
}
