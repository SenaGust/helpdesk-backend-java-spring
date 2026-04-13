package com.senagust.helpdesk.controller;

import com.senagust.helpdesk.dto.CreateUserRequest;
import com.senagust.helpdesk.dto.CreateUserResponse;
import com.senagust.helpdesk.service.UserService;
import com.senagust.helpdesk.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest newUser) {
        var createdUser = userService.create(newUser);
        return ResponseEntity.ok(createdUser);
    }
}
