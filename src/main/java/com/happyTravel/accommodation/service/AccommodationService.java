package com.happyTravel.accommodation.service;

import com.happyTravel.accommodation.dto.AccommodationEnrollDTO;
import com.happyTravel.accommodation.repository.AccommodationRepository;
import com.happyTravel.common.entity.accommodation.AccommodationColumnEntity;
import com.happyTravel.common.entity.common.RegUpdtInfo;
import com.happyTravel.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    /**
     * 숙소 등록 메서드
     */
    public CommonResponse registerAccommodation(AccommodationEnrollDTO accommodationEnrollDTO) {
        // RegUpdtInfo 생성
        RegUpdtInfo regUpdtInfo = RegUpdtInfo.builder()
                .regUser(accommodationEnrollDTO.getUserId())
                .regDtm(LocalDateTime.now())
                .build();

        // AccommodationColumnEntity 생성
        AccommodationColumnEntity accommodation = AccommodationColumnEntity.builder()
                .userId(accommodationEnrollDTO.getUserId())
                .name(accommodationEnrollDTO.getName())
                .description(accommodationEnrollDTO.getDescription())
                .address(accommodationEnrollDTO.getAddress())
                .phoneNo(accommodationEnrollDTO.getPhoneNo())
                .pricePerNight(accommodationEnrollDTO.getPricePerNight())
                .status(accommodationEnrollDTO.getStatus())
                .maxOccupancy(accommodationEnrollDTO.getMaxOccupancy())
                .regUpdtInfo(regUpdtInfo)
                .build();

        // 저장
        accommodationRepository.save(accommodation);

        //  응답 DTO 생성
        CommonResponse response = CommonResponse.builder()
                .message("등록 완료되었습니다.")
                .httpStatus(HttpStatus.CREATED.value()) // HTTP 상태 코드 설정
                .errorCode(null) // 에러 코드 설정 (성공 시 null)
                .build();

        return response;
    }

}
