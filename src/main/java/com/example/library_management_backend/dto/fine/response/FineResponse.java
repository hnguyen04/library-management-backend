package com.example.library_management_backend.dto.fine.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FineResponse {
    String id;
    String userId;
    String userName;
    String bookLoanId;
    String bookTitle;
    double amount;
    Date createdAt;
    Date updatedAt;
}