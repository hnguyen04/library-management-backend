package com.example.library_management_backend.mapper;

import com.example.library_management_backend.dto.role.request.RoleRequest;
import com.example.library_management_backend.dto.role.request.RoleUpdateRequest;
import com.example.library_management_backend.dto.role.response.RoleResponse;
import com.example.library_management_backend.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateRole(@MappingTarget Role role, RoleUpdateRequest request);
}
