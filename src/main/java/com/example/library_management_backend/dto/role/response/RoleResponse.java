package com.example.library_management_backend.dto.role.response;

import com.example.library_management_backend.constants.RoleEnum;
import com.example.library_management_backend.entity.Permission;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class RoleResponse {
    int id;
    @Enumerated(EnumType.STRING)
    RoleEnum name;
    Set<Permission> permissions;
}
