package com.senagust.helpdesk.service;

import com.senagust.helpdesk.dto.LoginRequest;
import com.senagust.helpdesk.dto.TokenResponse;

public interface IAuthService {
    TokenResponse login(LoginRequest request);
}
