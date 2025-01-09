package com.happyTravel.admin.repository;

import com.happyTravel.common.entity.admin.AdminColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface AdminAccountRepository extends JpaRepository<AdminColumnEntity, String> {

    AdminColumnEntity findByUserId(String loginUserId);

    // userId가 존재하는지 확인하는 메서드
    boolean existsByUserId(String userId);

}
