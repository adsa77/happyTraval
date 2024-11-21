package com.happyTravel.user.repository;

import com.happyTravel.common.entity.user.UserColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * {@link UserAccountRepository}는 사용자 관련 데이터베이스 작업을 처리하는 레포지토리입니다.
 * <p>
 * 이 레포지토리는 {@link UserColumnEntity} 엔티티에 대한 CRUD 작업을 처리하는 데 사용됩니다.
 * JpaRepository를 상속받아 기본적인 데이터베이스 접근 메서드를 제공하며,
 * 필요한 경우 추가적인 JPQL 또는 SQL 쿼리를 통해 커스텀한 데이터 조회 메서드를 구현합니다.
 * </p>
 *
 * 주요 메서드:
 * <ul>
 *   <li>{@link #findByUserId(String)}: 사용자 ID로 사용자 정보를 조회</li>
 *   <li>{@link #findMaxSequenceByUserId(String)}: 특정 사용자에 대한 최대 시퀀스 값을 조회</li>
 * </ul>
 *
 * @author 손지욱
 * @since 24.09.01
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserColumnEntity, String> {

    /**
     * 주어진 사용자 ID로 사용자를 조회하는 메서드입니다.
     *
     * @param loginUserId 사용자 ID
     * @return 사용자 정보(UserColumnEntity)
     */
    UserColumnEntity findByUserId(String loginUserId);

    /**
     * 주어진 사용자 ID에 대한 최대 시퀀스를 조회하는 메서드입니다.
     * <p>
     * GREATEST() 함수는 두 개의 값을 비교하여 더 큰 값을 반환합니다.
     * COALESCE() 함수는 주어진 인자 중 NULL이 아닌 첫 번째 값을 반환합니다.
     * r.sequence는 RequiredTermsAgreeEntity 테이블에서 sequence 컬럼의 최대값이며,
     * o.sequence는 OptionalTermsAgreeEntity 테이블에서 sequence 컬럼의 최대값입니다.
     * COALESCE(MAX(r.sequence), 0)와 COALESCE(MAX(o.sequence), 0)을 비교하여 더 큰 값을 반환합니다.
     * LEFT JOIN은 필수와 선택 약관을 동시에 검색하기 위해 사용됩니다.
     * 사용자가 선택 약관에 동의하지 않았지만 필수 약관에는 동의한 경우에도 필수 약관의 sequence 값을 반환할 수 있습니다.
     *
     * @param userId 사용자 ID
     * @return 주어진 사용자 ID에 대한 최대 시퀀스 값
     */
    @Query("""
            SELECT GREATEST(COALESCE(MAX(r.sequence), 0), COALESCE(MAX(o.sequence), 0))
            FROM RequiredTermsAgreeEntity r
            LEFT JOIN OptionalTermsAgreeEntity o ON r.userId = o.userId
            WHERE r.userId = :userId
            """)
    int findMaxSequenceByUserId(@Param("userId") String userId);
}
