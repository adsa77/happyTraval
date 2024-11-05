package com.happyTravel.user.repository;

import com.happyTravel.common.entity.UserColumnEntity;
import com.happyTravel.common.entity.RequiredTermsAgreeEntity; // RequiredTermsAgreeEntity 임포트 추가
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserColumnEntity, String> {

    UserColumnEntity findByUserId(String loginUserId);

    // 주어진 사용자 ID에 대한 최대 시퀀스를 조회하는 메서드 추가
//    @Query("SELECT COALESCE(MAX(sequence), 0) FROM RequiredTermsAgreeEntity WHERE userId = :userId")
//    int findMaxSequenceByUserId(@Param("userId") String userId);

    @Query("SELECT GREATEST(COALESCE(MAX(r.sequence), 0), COALESCE(MAX(o.sequence), 0)) " +
            "FROM RequiredTermsAgreeEntity r " +
            "LEFT JOIN OptionalTermsAgreeEntity o ON r.userId = o.userId " +
            "WHERE r.userId = :userId")
    int findMaxSequenceByUserId(@Param("userId") String userId);


}
