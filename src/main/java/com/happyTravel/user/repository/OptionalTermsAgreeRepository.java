package com.happyTravel.user.repository;

import com.happyTravel.common.entity.terms.OptionalTermsAgreeEntity;
import com.happyTravel.common.entity.pk.OptionalTermsAgreePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionalTermsAgreeRepository extends JpaRepository<OptionalTermsAgreeEntity, OptionalTermsAgreePk> {

}
