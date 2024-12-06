package com.example.library_management_backend.dto.book.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateRequest {
    int id;

    @NotBlank(message = "Title is mandatory")
    String title;

    @NotNull(message = "Price is mandatory")
    double price;

    String description;

    @NotNull(message = "Publisher ID is mandatory")
    int publisherId;

    @NotNull(message = "Author IDs are mandatory")
    Set<Integer> authorIds;

    @NotNull(message = "Category IDs are mandatory")
    Set<Integer> categoryIds;
}