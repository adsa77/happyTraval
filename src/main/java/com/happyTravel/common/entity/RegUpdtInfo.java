package com.happyTravel.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RegUpdtInfo {

    @Column(name = "REG_USER", length = 50)
    private String regUser;

    @Column(name = "REG_DTM")
    @Builder.Default
    private LocalDateTime regDtm = LocalDateTime.now();

    @Column(name = "UPDT_USER", length = 50)
    private String updtUser;

    @Column(name = "UPDT_DTM")
    private LocalDateTime updtDtm;

}
