package com.happyTravel.user.repository;

import com.happyTravel.common.entity.terms.RequiredTermsAgreeEntity;
import com.happyTravel.common.entity.pk.RequiredTermsAgreePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequiredTermsAgreeRepository extends JpaRepository<RequiredTermsAgreeEntity, RequiredTermsAgreePk> {

}
