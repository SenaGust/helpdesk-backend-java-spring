package com.senagust.helpdesk.service.impl;

import com.senagust.helpdesk.dto.LoginRequest;
import com.senagust.helpdesk.dto.TokenResponse;
import com.senagust.helpdesk.service.IAuthService;
import com.senagust.helpdesk.service.IJwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final IJwtService jwtService;

    @Override
    public TokenResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var userDetails = (UserDetails) authentication.getPrincipal();
        var token = jwtService.generateToken(userDetails);

        return new TokenResponse(token);
    }
}
