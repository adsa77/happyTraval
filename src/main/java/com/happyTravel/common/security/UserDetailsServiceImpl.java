package com.happyTravel.common.security;

import com.happyTravel.common.entity.user.UserColumnEntity;
import com.happyTravel.common.error.ErrorCode;
import com.happyTravel.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * {@link UserDetailsServiceImpl}은 Spring Security의 {@link UserDetailsService} 인터페이스를 구현하여
 * 사용자 인증을 위한 사용자 정보를 제공하는 서비스입니다.
 * <p>
 * 이 서비스는 주어진 사용자 ID에 대해 DB에서 사용자를 조회하고, 조회된 사용자 정보를
 * {@link UserDetails} 객체로 변환하여 반환합니다. 이를 통해 Spring Security가 해당 사용자의
 * 인증을 처리할 수 있게 됩니다.
 * </p>
 *
 * @since 24.09.01
 * @author 손지욱
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAccountRepository userRepository;

    /**
     * 주어진 사용자 ID로 사용자 정보를 로드하여 {@link UserDetails} 객체로 반환하는 메서드입니다.
     * <p>
     * 사용자 ID를 통해 DB에서 사용자 정보를 조회한 후, {@link org.springframework.security.core.userdetails.User}
     * 객체로 변환하여 반환합니다. 만약 사용자가 존재하지 않으면 {@link UsernameNotFoundException} 예외가 발생합니다.
     * </p>
     *
     * @param userId 사용자 ID
     * @return 사용자의 {@link UserDetails} 객체
     * @throws UsernameNotFoundException 사용자가 존재하지 않을 경우 발생
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        // 사용자 정보를 DB에서 조회
        UserColumnEntity userColumnEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        // UserDetails 객체로 반환 (Spring Security가 사용)
        return new org.springframework.security.core.userdetails.User(
                userColumnEntity.getUserId(),
                userColumnEntity.getUserPwd(),
                new ArrayList<>()); // 권한 리스트를 설정할 필요가 있으면 이 부분을 수정
    }

}
