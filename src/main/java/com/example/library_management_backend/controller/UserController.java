package com.example.library_management_backend.controller;

import com.example.library_management_backend.dto.ApiResponse;
import com.example.library_management_backend.dto.user.request.UserCreationRequest;
import com.example.library_management_backend.dto.user.request.UserGetAllRequest;
import com.example.library_management_backend.dto.user.request.UserUpdateRequest;
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
    ApiResponse<List<UserResponse>> getAllUser(
            @RequestParam(value = "skipCount", defaultValue = "0") int skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") int maxResultCount,
            @ModelAttribute UserGetAllRequest request) {

        request.setSkipCount(skipCount);
        request.setMaxResultCount(maxResultCount);
        return ApiResponse.<List<UserResponse>>builder().
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
    ApiResponse<UserResponse> updateUser(@RequestParam String id, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder().
                result(userService.updateUser(id, request)).
                build();
    }
}