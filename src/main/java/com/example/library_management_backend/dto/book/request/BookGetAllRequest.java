package com.example.library_management_backend.dto.book.request;

import com.example.library_management_backend.dto.base.BaseGetAllRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookGetAllRequest extends BaseGetAllRequest {
    String title;
    Integer publisherId;
    Set<Integer> authorIds;
    Set<Integer> categoryIds;
}