package com.happyTravel.common.entity.common;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "COMN_GROUP_TC")
public class ComnGroupEntity {

    @Id
    @Column(name = "GROUP_CD", length = 50, nullable = false)
    private String groupCd;

    @Column(name = "GROUP_NM", length = 50)
    private String groupNm;

}
