package com.senagust.helpdesk.dto;

import com.senagust.helpdesk.validation.ValidPassword;
import lombok.Data;

@Data
public class UpdateUserPasswordRequest {
    @ValidPassword
    private String password;
}
