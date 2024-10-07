package com.happyTravel.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "AUTH_TB")
public class AuthEntity {

    @Id
    @Column(name = "AUTH_CD", length = 100, nullable = false)
    private String authCd;

    @Column(name = "AUTH_NM", length = 1000)
    private String authNm;

}
