package com.senagust.helpdesk.mapper;

import com.senagust.helpdesk.dto.CreateUserResponse;
import com.senagust.helpdesk.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    CreateUserResponse toCreateUserResponse(User user);
}
