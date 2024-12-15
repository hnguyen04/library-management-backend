package com.example.library_management_backend.dto.book_loan.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanRequestReturnRequest {
    String bookLoanId;
    Date actualReturnDate;
}