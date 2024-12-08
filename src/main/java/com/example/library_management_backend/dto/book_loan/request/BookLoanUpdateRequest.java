package com.example.library_management_backend.dto.book_loan.request;

import com.example.library_management_backend.constants.BookLoanStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanUpdateRequest {
    @NotNull(message = "Book Loan ID is required")
    String id;
    String bookCopyId;
    String userId;
    Date loanDate;
    int numberOfDaysLoan;
    Date returnDate;
    Date actualReturnDate;
    @NotNull(message = "Status is required")
    BookLoanStatusEnum status;
}