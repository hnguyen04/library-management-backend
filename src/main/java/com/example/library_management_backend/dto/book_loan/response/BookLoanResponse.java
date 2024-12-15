package com.example.library_management_backend.dto.book_loan.response;

import com.example.library_management_backend.constants.BookLoanStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanResponse {
    String id;
    String bookCopyId;
    String userId;
    String userName;
    Date loanDate;
    Date returnDate;
    Date actualReturnDate;
    BookLoanStatusEnum status;
    String bookTitle;
    String bookRequestId;
}