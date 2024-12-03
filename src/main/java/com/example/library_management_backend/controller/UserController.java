package com.example.library_management_backend.controller;

import com.example.library_management_backend.dto.authen.request.IntrospectRequest;
import com.example.library_management_backend.dto.base.response.ApiResponse;
import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.user.request.*;
import com.example.library_management_backend.dto.user.response.UserConfigurationResponse;
import com.example.library_management_backend.dto.user.response.UserResponse;
import com.example.library_management_backend.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/Create")
    ApiResponse<UserResponse> createUser(@RequestBody UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder().
                result(userService.createUser(request)).
                build();
    }

    @GetMapping("/GetAll")
    ApiResponse<BaseGetAllResponse<UserResponse>> getAllUser(
            @RequestParam(value = "skipCount", defaultValue = "0") int skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") int maxResultCount,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "roleId", required = false) Integer roleId
    ) {

        UserGetAllRequest request = UserGetAllRequest.builder()
                .roleId(roleId)
                .name(name)
                .Email(email)
                .build();
        request.setSkipCount(skipCount);
        request.setMaxResultCount(maxResultCount);

        return ApiResponse.<BaseGetAllResponse<UserResponse>>builder().
                result(userService.getAllUsers(request)).
                build();
    }

    @GetMapping("/GetById")
    ApiResponse<UserResponse> getUserById(@RequestParam String id) {
        return ApiResponse.<UserResponse>builder().
                result(userService.getUser(id)).
                build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deleteUser(@RequestParam String id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder().
                result("User deleted successfully").
                build();
    }

    @PutMapping("/Update")
    ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder().
                result(userService.updateUser(request)).
                build();
    }

    @GetMapping("/MyInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder().
                result(userService.getMyInfo()).
                build();
    }

    @DeleteMapping("/DeleteMany")
    ApiResponse<String> deleteManyUser(@RequestBody List<String> ids) {
        userService.deleteManyUsers(ids);
        return ApiResponse.<String>builder().
                result("Users deleted successfully").
                build();
    }

    @PutMapping("/ChangePassword")
    ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ApiResponse.<String>builder().
                result("Password changed successfully").
                build();
    }

    @GetMapping("/GetAllConfigurations")
    ApiResponse<UserConfigurationResponse> getAllConfigurations() {
        return ApiResponse.<UserConfigurationResponse>builder().
                result(userService.getAllConfigurations()).
                build();
    }

    @PutMapping("/ResetPassword")
    ApiResponse<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request);
        return ApiResponse.<Void>builder().build();

    }
}