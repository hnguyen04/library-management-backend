package com.example.library_management_backend.dto.user.response;

import com.example.library_management_backend.constants.RoleEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserConfigurationResponse {
    int roleId;
    RoleEnum roleName;

    AuthPermissions auth;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class AuthPermissions {
        Map<String, Boolean> allPermissions;      // Tất cả các permission
        Map<String, Boolean> grantedPermissions; // Chỉ các permission được cấp
    }
}