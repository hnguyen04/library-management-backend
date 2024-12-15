package com.example.library_management_backend.dto.book_request.response;

import com.example.library_management_backend.constants.BookRequestStatusEnum;
import com.example.library_management_backend.constants.BookRequestTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequestResponse {
    String id;
    String bookCopyId;
    String bookTitle;
    String userId;
    String userName;
    BookRequestTypeEnum type;
    BookRequestStatusEnum status;
    Date createdAt;
    Date updatedAt;
}