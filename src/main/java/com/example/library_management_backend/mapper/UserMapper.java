package com.example.library_management_backend.mapper;

import com.example.library_management_backend.dto.user.request.UserCreationRequest;
import com.example.library_management_backend.dto.user.request.UserUpdateRequest;
import com.example.library_management_backend.dto.user.response.UserResponse;
import com.example.library_management_backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

//  @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
