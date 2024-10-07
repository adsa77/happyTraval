package com.happyTravel.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "COMN_CODE_TC")
public class ComnCodeEntity {

    @Id
    @Column(name = "COMN_CD", length = 50, nullable = false)
    private String comnCd;

    @Column(name = "GROUP_CD", length = 50, nullable = false)
    private String groupCd;

    @Column(name = "COMN_NM", length = 50)
    private String comnNm;

    @ManyToOne
    @JoinColumn(name = "GROUP_CD", insertable = false, updatable = false)
    private ComnGroupEntity comnGroup;

}
