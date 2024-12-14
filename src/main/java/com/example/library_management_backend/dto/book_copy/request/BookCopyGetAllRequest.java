package com.example.library_management_backend.dto.book_copy.request;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyGetAllRequest {
    String bookId;
    String bookTitle;
    BookCopyStatusEnum status;
    Integer skipCount;
    Integer maxResultCount;
}