package com.example.library_management_backend.dto.user.response;

import com.example.library_management_backend.constants.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String name;
    String userName;
    String email;
    Date createdAt;
    Date updatedAt;
    int roleId;
    @Enumerated(EnumType.STRING)
    RoleEnum roleName;
}
