package com.example.library_management_backend.dto.book.response;

import com.example.library_management_backend.dto.author.response.AuthorResponse;
import com.example.library_management_backend.dto.category.response.CategoryResponse;
import com.example.library_management_backend.dto.publisher.response.PublisherResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    int id;
    String title;
    double price;
    String description;
    PublisherResponse publisher;
    Set<AuthorResponse> authors;
    Set<CategoryResponse> categories;
    Date createdAt;
    Date updatedAt;
}