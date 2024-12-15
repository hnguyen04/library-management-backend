package com.example.library_management_backend.service;

import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.permission.request.PermissionGetAllRequest;
import com.example.library_management_backend.dto.permission.request.PermissionRequest;
import com.example.library_management_backend.dto.permission.request.PermissionUpdateRequest;
import com.example.library_management_backend.dto.permission.response.PermissionResponse;
import com.example.library_management_backend.entity.Permission;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.PermissionMapper;
import com.example.library_management_backend.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        try {
            permission = permissionRepository.save(permission);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        return permissionMapper.toPermissionResponse(permission);
    }

    public BaseGetAllResponse<PermissionResponse> getAllPermission(PermissionGetAllRequest request) {
        int skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        int maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;

        List<PermissionResponse> permissionList =  permissionRepository.findAll()
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(permissionMapper::toPermissionResponse)
                .toList();

        return BaseGetAllResponse.<PermissionResponse>builder()
                .data(permissionList)
                .totalRecords(permissionRepository.count())
                .build();
    }

    public PermissionResponse getPermissionById(int id) {
        Permission permission = permissionRepository.findById(String.valueOf(id)).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        return permissionMapper.toPermissionResponse(permission);
    }

    public PermissionResponse updatePermission(PermissionUpdateRequest request) {
        Permission permission = permissionRepository.findById(String.valueOf(request.getId())).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permissionMapper.updatePermission(permission, request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public void deletePermission(int id) {
        Permission permission = permissionRepository.findById(String.valueOf(id)).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        permissionRepository.delete(permission);
    }
}
