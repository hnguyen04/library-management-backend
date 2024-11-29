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
public class BookCopyCreationRequest {
    @NotNull(message = "Book ID is mandatory")
    int bookId;
    @NotNull(message = "status is mandatory")
    BookCopyStatusEnum status = BookCopyStatusEnum.AVAILABLE;
}