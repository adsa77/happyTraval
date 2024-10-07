package com.happyTravel.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TERMS_AGREE_TH")
public class TermsAgreeEntity {

    @Id
    @Column(name = "USER_TYPE", length = 1, nullable = false)
    private String userType;

    @Id
    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    @Id
    @Column(name = "TEMPLATE_SQ", nullable = false)
    private int templateSq;

    @Column(name = "AGREE_FL", length = 1, nullable = false)
    private String agreeFl;

    @Column(name = "AGREE_DT")
    private LocalDate agreeDt;

    @Embedded
    private RegUpdtInfo regUpdtInfo;

    @ManyToOne
    @JoinColumn(name = "TEMPLATE_SQ", insertable = false, updatable = false)
    private TemplateEntity template;

}
