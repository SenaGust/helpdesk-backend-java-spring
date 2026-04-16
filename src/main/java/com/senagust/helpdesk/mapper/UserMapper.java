package com.senagust.helpdesk.mapper;

import com.senagust.helpdesk.dto.UserResponse;
import com.senagust.helpdesk.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
