package com.senagust.helpdesk.service;

import com.senagust.helpdesk.dto.CreateUserRequest;
import com.senagust.helpdesk.dto.CreateUserResponse;

public interface UserService {
    CreateUserResponse create(CreateUserRequest request);
}
