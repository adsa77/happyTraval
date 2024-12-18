package com.happyTravel.partner.repository;

import com.happyTravel.common.entity.partner.PartnerColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PartnerAccountRepository extends JpaRepository<PartnerColumnEntity, String>  {

    PartnerColumnEntity findByUserId(String loginUserId);

    // userId가 존재하는지 확인하는 메서드
    boolean existsByUserId(String userId);

}
