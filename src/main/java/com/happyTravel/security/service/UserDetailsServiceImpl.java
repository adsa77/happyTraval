package com.happyTravel.security.service;

import com.happyTravel.admin.repository.AdminAccountRepository;
import com.happyTravel.common.entity.admin.AdminColumnEntity;
import com.happyTravel.common.entity.partner.PartnerColumnEntity;
import com.happyTravel.common.entity.user.UserColumnEntity;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.partner.repository.PartnerAccountRepository;
import com.happyTravel.user.repository.UserAccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link UserDetailsServiceImpl}은 Spring Security의 {@link UserDetailsService} 인터페이스를 구현하여
 * 사용자 인증을 위한 사용자 정보를 제공하는 서비스입니다.
 * <p>
 * 이 서비스는 주어진 사용자 ID에 대해 DB에서 사용자를 조회하고, 조회된 사용자 정보를
 * {@link UserDetails} 객체로 변환하여 반환합니다. 이를 통해 Spring Security가 해당 사용자의
 * 인증을 처리할 수 있게 됩니다.
 * </p>
 *
 * <h2>Implementation</h2>
 * <p>
 * {@link UserDetailsServiceImpl} 클래스는 {@link UserDetailsService}의 <code>loadUserByUsername</code>
 * 메서드를 구현하며, 이 메서드는 다음과 같은 단계로 구성됩니다:
 * </p>
 * <ul>
 *     <li><strong>사용자 조회:</strong> {@link UserAccountRepository}를 통해 사용자 ID로 사용자를 조회합니다.</li>
 *     <li><strong>예외 처리:</strong> 사용자가 존재하지 않을 경우 {@link UsernameNotFoundException} 예외를 발생시킵니다.</li>
 *     <li><strong>변환:</strong> 조회된 사용자 정보를 {@link UserDetails} 객체로 변환합니다.
 *         이 과정에서 사용자 권한(roles, authorities)도 함께 매핑합니다.</li>
 * </ul>
 *
 * <p>
 * 이를 통해 Spring Security가 사용자 인증 과정에서 필요한 정보를 정확히 제공할 수 있습니다.
 * 또한, 확장 가능한 구조로 설계되어 권한 관리 로직을 커스터마이징할 수 있습니다.
 * </p>
 *
 * @since 24.09.01
 * @author 손지욱
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;
    private final AdminAccountRepository adminAccountRepository;
    private final PartnerAccountRepository partnerAccountRepository;
    private final HttpServletRequest request;

    @Autowired
    public UserDetailsServiceImpl(UserAccountRepository userAccountRepository,
                                  AdminAccountRepository adminAccountRepository,
                                  PartnerAccountRepository partnerAccountRepository,
                                  HttpServletRequest request) {
        this.userAccountRepository = userAccountRepository;
        this.adminAccountRepository = adminAccountRepository;
        this.partnerAccountRepository = partnerAccountRepository;
        this.request = request;
    }

    /**
     * 사용자 ID로 사용자 정보를 조회하여 반환하는 메소드 (USER)
     *
     * @param userId 사용자 ID
     * @return {@link UserDetails} 객체
     * @throws UsernameNotFoundException 사용자가 존재하지 않으면 예외 발생
     */
    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        UserColumnEntity user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getUserPwd(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    /**
     * 관리자 ID로 관리자 정보를 조회하여 반환하는 메소드 (ADMIN)
     *
     * @param adminId 관리자 ID
     * @return {@link UserDetails} 객체
     * @throws UsernameNotFoundException 사용자가 존재하지 않으면 예외 발생
     */
    public UserDetails loadUserByAdminId(String adminId) throws UsernameNotFoundException {
        AdminColumnEntity admin = adminAccountRepository.findById(adminId)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
        return new org.springframework.security.core.userdetails.User(
                admin.getUserId(),
                admin.getUserPwd(),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }

    /**
     * 파트너 ID로 파트너 정보를 조회하여 반환하는 메소드 (PARTNER)
     *
     * @param partnerId 파트너 ID
     * @return {@link UserDetails} 객체
     * @throws UsernameNotFoundException 사용자가 존재하지 않으면 예외 발생
     */
    public UserDetails loadUserByPartnerId(String partnerId) throws UsernameNotFoundException {
        PartnerColumnEntity partner = partnerAccountRepository.findById(partnerId)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
        return new org.springframework.security.core.userdetails.User(
                partner.getUserId(),
                partner.getUserPwd(),
                List.of(new SimpleGrantedAuthority("ROLE_PARTNER"))
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("This method is not supported. Use specific methods for each role instead.");
    }

}
