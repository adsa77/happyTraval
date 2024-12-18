package com.happyTravel.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authenticationManager.authenticate(authentication); // 실제 인증 수행
    }
}
