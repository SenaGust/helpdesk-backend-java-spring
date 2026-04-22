package com.senagust.helpdesk.service.impl;

import com.senagust.helpdesk.model.JwtUserDetails;
import com.senagust.helpdesk.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new JwtUserDetails(user);
    }

    public UserDetails loadUserById(UUID id) {
        var user = userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));

        return new JwtUserDetails(user);
    }
}
