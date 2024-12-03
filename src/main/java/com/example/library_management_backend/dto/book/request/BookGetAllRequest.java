package com.example.library_management_backend.dto.book.request;

import com.example.library_management_backend.dto.base.request.BaseGetAllRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookGetAllRequest extends BaseGetAllRequest {
    String title;
    Integer publisherId;
    Integer authorId;
    Integer categoryId;
}