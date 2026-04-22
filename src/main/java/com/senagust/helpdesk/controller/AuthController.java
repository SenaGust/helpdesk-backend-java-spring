package com.senagust.helpdesk.controller;

import com.senagust.helpdesk.dto.LoginRequest;
import com.senagust.helpdesk.dto.TokenResponse;
import com.senagust.helpdesk.service.IAuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        var authToken = authService.login(loginRequest);

        return ResponseEntity.ok(authToken);
    }
}
