package com.example.library_management_backend.dto.book_loan.request;

import com.example.library_management_backend.constants.BookLoanStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanGetAllRequest {
    String bookTitle;
    BookLoanStatusEnum status;
    String userId;
    Integer skipCount;
    Integer maxResultCount;
}