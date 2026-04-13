package com.senagust.helpdesk.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
}
