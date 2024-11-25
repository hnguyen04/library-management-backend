package com.example.library_management_backend.dto.user.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String name;
    String userName;
    String email;
    @Size(min = 8, message = "Password must be at least 8 characters")
    String password;
    int roleId;
}
