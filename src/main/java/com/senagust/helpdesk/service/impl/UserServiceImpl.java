package com.senagust.helpdesk.service.impl;

import com.senagust.helpdesk.dto.CreateUserRequest;
import com.senagust.helpdesk.dto.CreateUserResponse;
import com.senagust.helpdesk.mapper.UserMapper;
import com.senagust.helpdesk.model.Customer;
import com.senagust.helpdesk.model.ServiceProvider;
import com.senagust.helpdesk.model.User;
import com.senagust.helpdesk.model.UserType;
import com.senagust.helpdesk.repository.UserRepository;
import com.senagust.helpdesk.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public CreateUserResponse create(CreateUserRequest request) {
        User user;

        if (request.getUserType() == UserType.SERVICE_PROVIDER) {
            ServiceProvider serviceProvider = new ServiceProvider();
            serviceProvider.setAvailableSlotHours(request.getAvailableSlotHours());
            user = serviceProvider;
        } else {
            user = new Customer();
        }

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setActive(true);

        var createdUser = userRepository.save(user);

        return userMapper.toCreateUserResponse(createdUser);
    }

    @Override
    public List<CreateUserResponse> getAll() {
        var users = userRepository.findAll();
        
        return users.stream().map(userMapper::toCreateUserResponse).toList();
    }
}
