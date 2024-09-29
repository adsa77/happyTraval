package com.happyTravel.userLogin.repository;

import com.happyTravel.userLogin.entity.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEntity, String> {
    UserLoginEntity findByUserId(String userId);
}
