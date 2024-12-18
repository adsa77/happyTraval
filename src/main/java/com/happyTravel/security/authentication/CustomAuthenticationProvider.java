package com.happyTravel.security.authentication;

import com.happyTravel.admin.repository.AdminAccountRepository;
import com.happyTravel.common.entity.admin.AdminColumnEntity;
import com.happyTravel.common.entity.partner.PartnerColumnEntity;
import com.happyTravel.common.entity.user.UserColumnEntity;
import com.happyTravel.partner.repository.PartnerAccountRepository;
import com.happyTravel.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserAccountRepository userRepository;
    private final PartnerAccountRepository partnerRepository;
    private final AdminAccountRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = authentication.getName(); // 사용자 ID
        String password = (String) authentication.getCredentials(); // 비밀번호

        // 사용자 인증 처리
        if (userRepository.existsByUserId(userId)) {
            return authenticateUser(userId, password);
        }
        // 파트너 인증 처리
        else if (partnerRepository.existsByUserId(userId)) {
            return authenticatePartner(userId, password);
        }
        // 관리자 인증 처리
        else if (adminRepository.existsByUserId(userId)) {
            return authenticateAdmin(userId, password);
        }

        throw new BadCredentialsException("Invalid username or password");
    }

    private Authentication authenticateUser(String userId, String password) {
        UserColumnEntity user = userRepository.findByUserId(userId);
        if (user == null || !passwordEncoder.matches(password, user.getUserPwd())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userId, password);
    }

    private Authentication authenticatePartner(String partnerId, String password) {
        PartnerColumnEntity partner = partnerRepository.findByUserId(partnerId);
        if (partner == null || !passwordEncoder.matches(password, partner.getUserPwd())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(partnerId, password);
    }

    private Authentication authenticateAdmin(String adminId, String password) {
        AdminColumnEntity admin = adminRepository.findByUserId(adminId);
        if (admin == null || !passwordEncoder.matches(password, admin.getUserPwd())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(adminId, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
