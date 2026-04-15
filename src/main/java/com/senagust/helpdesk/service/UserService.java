package com.senagust.helpdesk.service;

import com.senagust.helpdesk.dto.CreateUserRequest;
import com.senagust.helpdesk.dto.CreateUserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    CreateUserResponse create(CreateUserRequest request);

    List<CreateUserResponse> getAll();

    CreateUserResponse getById(UUID userId);
}
