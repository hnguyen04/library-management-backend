package com.example.library_management_backend.dto.book_copy.response;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyResponse {
    String id;
    int bookId;
    String bookTitle;
    BookCopyStatusEnum status;
    Date createdAt;
    Date updatedAt;
}