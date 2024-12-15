package com.example.library_management_backend.mapper;

import com.example.library_management_backend.dto.permission.request.PermissionRequest;
import com.example.library_management_backend.dto.permission.request.PermissionUpdateRequest;
import com.example.library_management_backend.dto.permission.response.PermissionResponse;
import com.example.library_management_backend.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);

    @Mapping(target = "id", ignore = true)
    void updatePermission(@MappingTarget Permission permission, PermissionUpdateRequest request);
}
