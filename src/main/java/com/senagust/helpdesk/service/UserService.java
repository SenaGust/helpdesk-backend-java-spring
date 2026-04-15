package com.senagust.helpdesk.service;

import com.senagust.helpdesk.dto.CreateUserRequest;
import com.senagust.helpdesk.dto.CreateUserResponse;

import java.util.List;

public interface UserService {
    CreateUserResponse create(CreateUserRequest request);

    List<CreateUserResponse> getAll();
}
