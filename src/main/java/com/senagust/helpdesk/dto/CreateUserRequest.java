package com.senagust.helpdesk.dto;

import com.senagust.helpdesk.model.UserType;
import com.senagust.helpdesk.validation.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {
    @NotBlank
    @Email
    private String email;

    @ValidPassword
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private UserType userType;

    private List<Integer> availableSlotHours;
}
