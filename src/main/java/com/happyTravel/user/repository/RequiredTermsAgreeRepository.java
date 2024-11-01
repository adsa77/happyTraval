package com.happyTravel.user.repository;

import com.happyTravel.common.entity.RequiredTermsAgreeEntity;
import com.happyTravel.common.entity.RequiredTermsAgreePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequiredTermsAgreeRepository extends JpaRepository<RequiredTermsAgreeEntity, RequiredTermsAgreePk> {

}
