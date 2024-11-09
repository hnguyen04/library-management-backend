package com.example.library_management_backend.dto.publisher.request;

import com.example.library_management_backend.dto.base.BaseGetAllRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublisherGetAllRequest extends BaseGetAllRequest {
    String name;
}
