package com.example.library_management_backend.dto.publisher.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PublisherUpdateRequest {
    @NotBlank(message = "Name is mandatory")
    String name;
}