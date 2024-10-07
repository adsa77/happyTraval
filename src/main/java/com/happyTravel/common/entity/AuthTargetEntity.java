package com.happyTravel.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "AUTH_TH")
public class AuthTargetEntity {

    @Id
    @Column(name = "AUTH_TARGET", length = 50, nullable = false)
    private String authTarget;

    @Id
    @Column(name = "AUTH_TARGET_TYPE", length = 1, nullable = false)
    private String authTargetType;

    @Column(name = "AUTH_CD", length = 100, nullable = false)
    private String authCd;

    @Embedded
    private RegUpdtInfo regUpdtInfo;

    @ManyToOne
    @JoinColumn(name = "AUTH_CD", insertable = false, updatable = false)
    private AuthEntity auth;

}
