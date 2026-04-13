package com.senagust.helpdesk.dto;

import com.senagust.helpdesk.model.UserType;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private UserType userType;
    private List<Integer> availableSlotHours; // ← add this
}
