package com.happyTravel.partner.repository;

import com.happyTravel.common.entity.partner.PartnerColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerAccountRepository extends JpaRepository<PartnerColumnEntity, String>  {

    PartnerColumnEntity findByUserId(String loginUserId);

    // userId가 존재하는지 확인하는 메서드
    boolean existsByUserId(String userId);

    @Query("""
            SELECT GREATEST(COALESCE(MAX(r.sequence), 0), COALESCE(MAX(o.sequence), 0))
            FROM RequiredTermsAgreeEntity r
            LEFT JOIN OptionalTermsAgreeEntity o ON r.userId = o.userId
            WHERE r.userId = :userId
            """)
    int findMaxSequenceByUserId(@Param("userId") String userId);
}
