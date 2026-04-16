package com.senagust.helpdesk.controller;

import com.senagust.helpdesk.dto.CreateUserRequest;
import com.senagust.helpdesk.dto.UpdateUserPasswordRequest;
import com.senagust.helpdesk.dto.UpdateUserRequest;
import com.senagust.helpdesk.dto.UserResponse;
import com.senagust.helpdesk.service.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest newUser) {
        var createdUser = userService.create(newUser);

        URI location = URI.create("/users/" + createdUser.getId());

        return ResponseEntity.created(location).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        var allUsers = userService.getAll();

        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID userId) {
        var user = userService.getById(userId);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId) {
        userService.deleteById(userId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable UUID userId, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        var updatedUser = userService.updateById(userId, updateUserRequest);

        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/{userId}/password")
    public ResponseEntity<Void> changePasswordById(@PathVariable UUID userId, @Valid @RequestBody UpdateUserPasswordRequest newPassword) {
        userService.changePasswordById(userId, newPassword.getPassword());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/reactivate")
    public ResponseEntity<Void> reactivateUserById(@PathVariable UUID userId) {
        userService.reactivateById(userId);

        return ResponseEntity.noContent().build();
    }
}
