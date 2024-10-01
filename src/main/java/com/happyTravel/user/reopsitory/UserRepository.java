package com.happyTravel.user.reopsitory;

import com.happyTravel.common.entity.UserColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserColumnEntity, String> {

    UserColumnEntity findByUserId(String loginUserId);

}
