package com.happyTravel.common.entity.common;

import com.happyTravel.common.entity.terms.TemplateEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ALARM_SEND_TH")
public class AlarmSendEntity {

    @Id
    @Column(name = "ALARM_SEND_SQ", nullable = false)
    private long alarmSendSq;

    @Column(name = "TEMPLATE_SQ", nullable = false)
    private int templateSq;

    @Column(name = "RECV_INFO", length = 300)
    private String recvInfo;

    @Column(name = "SEND_INFO", length = 50)
    private String sendInfo;

    @Column(name = "SEND_DTM")
    private LocalDateTime sendDtm;

    @Column(name = "RESERVATION_FL", length = 1)
    private String reservationFl;

    @Column(name = "SEND_RESULT", length = 50)
    private String sendResult;

    @Embedded
    private RegUpdtInfo regUpdtInfo;

    @ManyToOne
    @JoinColumn(name = "TEMPLATE_SQ", insertable = false, updatable = false)
    private TemplateEntity template;

}
