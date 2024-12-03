package com.example.library_management_backend.dto.author.request;

import com.example.library_management_backend.dto.base.request.BaseGetAllRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorGetAllRequest extends BaseGetAllRequest {
    String name;
}
