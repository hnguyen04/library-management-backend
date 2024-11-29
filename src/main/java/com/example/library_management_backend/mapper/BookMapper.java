package com.example.library_management_backend.mapper;

import com.example.library_management_backend.dto.book.request.BookCreationRequest;
import com.example.library_management_backend.dto.book.request.BookUpdateRequest;
import com.example.library_management_backend.dto.book.response.BookResponse;
import com.example.library_management_backend.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Book toBook(BookCreationRequest request);

    @Mapping(target = "publisherId", source = "publisher.id")
    @Mapping(target = "publisherName", source = "publisher.name")
    BookResponse toBookResponse(Book book);

    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateBook(@MappingTarget Book book, BookUpdateRequest request);
}