package com.example.library_management_backend.dto.book_request.request;


import com.example.library_management_backend.constants.BookLoanStatusEnum;
import com.example.library_management_backend.constants.BookRequestStatusEnum;
import com.example.library_management_backend.entity.BookRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequestUpdateRequest {
    @NotNull(message = "Book Loan ID is required")
    String id;
    BookRequestStatusEnum status;
}
