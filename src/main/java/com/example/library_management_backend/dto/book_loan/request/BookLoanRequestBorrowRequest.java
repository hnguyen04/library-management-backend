package com.example.library_management_backend.dto.book_loan.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookLoanRequestBorrowRequest {
    @NotNull(message = "Book ID is required")
    Integer bookId;
    @NotNull(message = "User ID is required")
    String userId;
    @NotNull(message = "Loan Date is required")
    Date loanDate;
    @NotNull(message = "Number of Days Loan is required")
    Integer numberOfDaysLoan;
}