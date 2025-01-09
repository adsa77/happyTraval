package com.happyTravel.accommodation.controller;

import com.happyTravel.accommodation.dto.AccommodationEnrollDTO;
import com.happyTravel.accommodation.service.AccommodationService;
import com.happyTravel.common.entity.accommodation.AccommodationColumnEntity;
import com.happyTravel.common.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 숙소 관련 API를 처리하는 REST 컨트롤러.
 * <p>
 * 숙소 관련
 * </p>
 *
 * @author 손지욱
 * @since 24.09.01
 */
@RestController
@RequestMapping("/api/accommodation")
@RequiredArgsConstructor
public class AccommodationRestController {

    private final AccommodationService accommodationService;

    /**
     * 숙소 등록 API
     */
    @PostMapping("enroll")
    public ResponseEntity<CommonResponse> enrollAccommodation(@Valid @RequestBody AccommodationEnrollDTO accommodationEnrollDTO) {
        CommonResponse response = accommodationService.registerAccommodation(accommodationEnrollDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
