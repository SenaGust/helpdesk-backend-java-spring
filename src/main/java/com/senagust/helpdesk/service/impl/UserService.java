package com.senagust.helpdesk.service.impl;

import com.senagust.helpdesk.dto.CreateUserRequest;
import com.senagust.helpdesk.dto.UpdateUserRequest;
import com.senagust.helpdesk.dto.UserResponse;
import com.senagust.helpdesk.exception.ConflictException;
import com.senagust.helpdesk.exception.NotFoundException;
import com.senagust.helpdesk.mapper.UserMapper;
import com.senagust.helpdesk.model.Customer;
import com.senagust.helpdesk.model.ServiceProvider;
import com.senagust.helpdesk.model.User;
import com.senagust.helpdesk.model.UserType;
import com.senagust.helpdesk.repository.UserRepository;
import com.senagust.helpdesk.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private User findActiveUserById(UUID userId) {
        return userRepository.findByIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already in use: " + request.getEmail());
        }

        User user;

        if (request.getUserType() == UserType.SERVICE_PROVIDER) {
            ServiceProvider serviceProvider = new ServiceProvider();
            serviceProvider.setAvailableSlotHours(request.getAvailableSlotHours());
            user = serviceProvider;
        } else {
            user = new Customer();
        }

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setActive(true);

        var createdUser = userRepository.save(user);

        return userMapper.toUserResponse(createdUser);
    }

    @Override
    public List<UserResponse> getAll() {
        var users = userRepository.findAllByIsActiveTrue();

        return users.stream().map(userMapper::toUserResponse).toList();
    }


    @Override
    public UserResponse getById(UUID userId) {
        User user = findActiveUserById(userId);

        return userMapper.toUserResponse(user);
    }


    @Override
    public void deleteById(UUID userId) {
        User user = findActiveUserById(userId);

        user.setActive(false);

        userRepository.save(user);
    }

    @Override
    public UserResponse updateById(UUID userId, UpdateUserRequest updateUserRequest) {
        User user = findActiveUserById(userId);

        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public void reactivateById(UUID userId) {
        User user = userRepository.
                findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        user.setActive(true);

        userRepository.save(user);
    }

    @Override
    public void changePasswordById(UUID userId, String newPassword) {
        User user = findActiveUserById(userId);

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }
}
