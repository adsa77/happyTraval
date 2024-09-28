package com.happyTravel.userSignUp.repository;

import com.happyTravel.userSignUp.entity.UserSignUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSignUpRepository extends JpaRepository<UserSignUpEntity, String> {

}
