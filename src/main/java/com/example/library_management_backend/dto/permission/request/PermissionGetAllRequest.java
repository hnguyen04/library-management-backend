package com.example.library_management_backend.dto.permission.request;

import com.example.library_management_backend.dto.base.request.BaseGetAllRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionGetAllRequest extends BaseGetAllRequest {
}
