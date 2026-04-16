package com.senagust.helpdesk.service;

import com.senagust.helpdesk.dto.CreateUserRequest;
import com.senagust.helpdesk.dto.UpdateUserRequest;
import com.senagust.helpdesk.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserResponse create(CreateUserRequest request);

    List<UserResponse> getAll();

    UserResponse getById(UUID userId);

    void deleteById(UUID userId);

    UserResponse updateById(UUID userId, UpdateUserRequest updateUserRequest);

    void reactivateById(UUID userId);

    void changePasswordById(UUID userId, String newPassword);
}
