package com.happyTravel.accommodation.repository;

import com.happyTravel.common.entity.accommodation.AccommodationColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<AccommodationColumnEntity, Integer> {
}
