package com.senagust.helpdesk.service.impl;

import com.senagust.helpdesk.dto.CreateUserRequest;
import com.senagust.helpdesk.dto.CreateUserResponse;
import com.senagust.helpdesk.exception.ConflictException;
import com.senagust.helpdesk.exception.NotFoundException;
import com.senagust.helpdesk.mapper.UserMapper;
import com.senagust.helpdesk.model.Customer;
import com.senagust.helpdesk.model.ServiceProvider;
import com.senagust.helpdesk.model.User;
import com.senagust.helpdesk.model.UserType;
import com.senagust.helpdesk.repository.UserRepository;
import com.senagust.helpdesk.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse create(CreateUserRequest request) {
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

        return userMapper.toCreateUserResponse(createdUser);
    }

    @Override
    public List<CreateUserResponse> getAll() {
        var users = userRepository.findAllByIsActiveTrue();

        return users.stream().map(userMapper::toCreateUserResponse).toList();
    }


    @Override
    public CreateUserResponse getById(UUID userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id" + userId));

        if (!user.isActive()) {
            throw new NotFoundException("User not found with id" + userId);
        }

        return userMapper.toCreateUserResponse(user);
    }


    @Override
    public void deleteById(UUID userId) {
        User user = userRepository.
                findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        user.setActive(false);

        userRepository.save(user);
    }
}
