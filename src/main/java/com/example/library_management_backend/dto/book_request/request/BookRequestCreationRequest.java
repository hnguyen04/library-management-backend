package com.example.library_management_backend.dto.book_request.request;

import com.example.library_management_backend.constants.BookLoanStatusEnum;
import com.example.library_management_backend.constants.BookRequestStatusEnum;
import com.example.library_management_backend.constants.BookRequestTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequestCreationRequest {
    String BookLoanId;
    BookRequestStatusEnum status;
    BookRequestTypeEnum type;
}
