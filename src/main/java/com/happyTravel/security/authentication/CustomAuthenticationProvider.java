package com.happyTravel.security.authentication;

import com.happyTravel.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = authentication.getName(); // 사용자 ID
        String password = (String) authentication.getCredentials(); // 비밀번호
        String role = (String) authentication.getDetails();  // 역할 (USER, PARTNER, ADMIN)

        if (role == null || role.isEmpty()) {
            throw new BadCredentialsException("Role must not be null or empty");
        }

        // 역할에 맞는 UserDetailsService 메소드 호출
        UserDetails userDetails = switch (role) {
            case "U" -> userDetailsService.loadUserByUserId(userId);
            case "A" -> userDetailsService.loadUserByAdminId(userId);
            case "P" -> userDetailsService.loadUserByPartnerId(userId);
            default -> throw new BadCredentialsException("Invalid user role");
        };

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // 인증 성공 후 UsernamePasswordAuthenticationToken 반환
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,  // 비밀번호는 보안상 null로 처리
                userDetails.getAuthorities()  // 권한 정보
        );

        // role을 details에 담아서 반환
        authToken.setDetails(role);  // role을 details에 설정

        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
