package com.happyTravel.common.entity.admin;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DEPT_TB")
public class DeptEntity {

    @Id
    @Column(name = "DEPT_CD", length = 2, nullable = false)
    private String deptCd;

    @Column(name = "DEPT_NM", length = 50)
    private String deptNm;

}
