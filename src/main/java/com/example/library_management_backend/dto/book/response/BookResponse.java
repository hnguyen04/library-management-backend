package com.example.library_management_backend.dto.book.response;

import com.example.library_management_backend.entity.Author;
import com.example.library_management_backend.entity.Category;
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
    int publisherId;
    long numberOfCopiesAvailable;
    String publisherName;
    Set<Author> authors;
    Set<Category> categories;
    Date createdAt;
    Date updatedAt;
}