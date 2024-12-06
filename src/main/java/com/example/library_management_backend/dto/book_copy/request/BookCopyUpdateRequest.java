package com.example.library_management_backend.dto.book_copy.request;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyUpdateRequest {
    @NotNull(message = "ID is mandatory")
    String id;

    @NotNull(message = "Status is mandatory")
    BookCopyStatusEnum status;
}