package com.example.library_management_backend.mapper;

import com.example.library_management_backend.dto.permission.request.PermissionRequest;
import com.example.library_management_backend.dto.permission.response.PermissionResponse;
import com.example.library_management_backend.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mapping(target = "id", ignore = true)
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);
}
