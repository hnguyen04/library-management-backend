package com.example.library_management_backend.dto.role.request;


import com.example.library_management_backend.constants.RoleEnum;
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
public class RoleRequest {
    @Enumerated(EnumType.STRING)
    RoleEnum name;
    Set<Integer> permissions;
}
