package com.happyTravel.accommodation.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 숙소 등록을 위한 DTO.
 * <p>
 * 클라이언트가 숙소 등록 시 보내는 요청 데이터를 담는 객체입니다.
 * </p>
 *
 * @author 손지욱
 * @since 24.09.01
 */
@Getter
@Setter
public class AccommodationEnrollDTO {

    private String userId;          // 파트너 ID
    private String name;            // 숙소 이름
    private String description;     // 숙소 설명
    private String address;         // 숙소 주소
    private String phoneNo;         // 숙소 전화번호
    private Integer pricePerNight;  // 1박당 가격
    private String status;          // 숙소 상태 ('A' - 활성, 'I' - 비활성, 'D' - 삭제)
    private Integer maxOccupancy;   // 최대 수용 인원

}
