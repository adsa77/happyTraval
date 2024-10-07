package com.happyTravel.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MARKETING_AGREE_TH")
public class MarketingAgreeEntity {

    @Id
    @Column(name = "AGREE_SQ", nullable = false)
    private long agreeSq;

    @Column(name = "TEMPLATE_SQ", nullable = false)
    private int templateSq;

    @Column(name = "USER_TYPE", length = 1)
    private String userType;

    @Column(name = "USER_ID", length = 50)
    private String userId;

    @Column(name = "AGREE_FL", length = 1)
    private String agreeFl;

    @Column(name = "AGREE_DTM")
    private LocalDateTime agreeDtm;

    @Column(name = "DAGREE_DTM")
    private LocalDateTime dagreeDtm;

    @Embedded
    private RegUpdtInfo regUpdtInfo;

    @ManyToOne
    @JoinColumn(name = "TEMPLATE_SQ", insertable = false, updatable = false)
    private TemplateEntity template;

}
