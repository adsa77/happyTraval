package com.happyTravel.user.repository;

import com.happyTravel.common.entity.OptionalTermsAgreeEntity;
import com.happyTravel.common.entity.OptionalTermsAgreePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionalTermsAgreeRepository extends JpaRepository<OptionalTermsAgreeEntity, OptionalTermsAgreePk> {

}
