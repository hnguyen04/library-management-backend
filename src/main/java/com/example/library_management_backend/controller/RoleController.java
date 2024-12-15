package com.example.library_management_backend.controller;

import com.example.library_management_backend.dto.base.response.ApiResponse;
import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.role.request.RoleRequest;
import com.example.library_management_backend.dto.role.request.RoleUpdateRequest;
import com.example.library_management_backend.dto.role.response.RoleResponse;
import com.example.library_management_backend.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping("/Create")
    ApiResponse<RoleResponse> createUser(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder().
                result(roleService.createRole(request)).
                build();
    }

    @GetMapping("/GetAll")
    ApiResponse<BaseGetAllResponse<RoleResponse>> getAllRole() {
        return ApiResponse.<BaseGetAllResponse<RoleResponse>>builder().
                result(roleService.getAllRole()).
                build();
    }

    @GetMapping("/GetById")
    ApiResponse<RoleResponse> getRoleById(@RequestParam int id) {
        return ApiResponse.<RoleResponse>builder().
                result(roleService.getRoleById(id)).
                build();
    }

    @PutMapping("/Update")
    ApiResponse<RoleResponse> updateRole(@RequestBody RoleUpdateRequest request) {
        return ApiResponse.<RoleResponse>builder().
                result(roleService.updateRole(request)).
                build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deleteRole(@RequestParam int id) {
        roleService.deleteRole(id);
        return ApiResponse.<String>builder().
                result("Role deleted successfully").
                build();
    }

    @PutMapping("/AddPermissions")
    ApiResponse<RoleResponse> addPermissions(@RequestParam int id, @RequestBody List<Integer> request) {
        return ApiResponse.<RoleResponse>builder().
                result(roleService.addPermissionstoRole(id, request)).
                build();
    }

    @PutMapping("/RemovePermission")
    ApiResponse<RoleResponse> removePermission(@RequestParam int id, @RequestParam Integer permissionId) {
        return ApiResponse.<RoleResponse>builder().
                result(roleService.removePermissionFromRole(id, permissionId)).
                build();
    }

}
