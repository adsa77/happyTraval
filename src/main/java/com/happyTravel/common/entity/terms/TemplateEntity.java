package com.happyTravel.common.entity.terms;

import com.happyTravel.common.entity.common.RegUpdtInfo;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TEMPLATE_TB")
public class TemplateEntity {

    @Id
    @Column(name = "TEMPLATE_SQ", nullable = false)
    private int templateSq;

    @Column(name = "MCATEGORY", length = 2)
    private String mcategory;

    @Column(name = "SCATEGORY", length = 2)
    private String scategory;

    @Column(name = "TITLE", length = 100)
    private String title;

    @Column(name = "CONTENT", length = 3000)
    private String content;

    @Column(name = "USE_FL", length = 1)
    private String useFl;

    @Embedded
    private RegUpdtInfo regUpdtInfo;

}
