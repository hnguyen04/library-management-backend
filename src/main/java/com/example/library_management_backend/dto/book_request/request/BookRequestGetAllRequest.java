package com.example.library_management_backend.dto.book_request.request;


import com.example.library_management_backend.constants.BookLoanStatusEnum;
import com.example.library_management_backend.constants.BookRequestStatusEnum;
import com.example.library_management_backend.constants.BookRequestTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequestGetAllRequest {
    String bookTitle;
    String userId;
    String userName;
    BookRequestStatusEnum status;
    BookRequestTypeEnum type;
    Integer skipCount;
    Integer maxResultCount;
}