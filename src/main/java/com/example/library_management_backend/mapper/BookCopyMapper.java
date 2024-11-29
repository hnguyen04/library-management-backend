package com.example.library_management_backend.mapper;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import com.example.library_management_backend.dto.book_copy.request.BookCopyCreationRequest;
import com.example.library_management_backend.dto.book_copy.request.BookCopyUpdateRequest;
import com.example.library_management_backend.dto.book_copy.response.BookCopyResponse;
import com.example.library_management_backend.entity.BookCopy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    @Mapping(target = "book.id", source = "bookId")
    @Mapping(target = "status", source = "status", qualifiedByName = "defaultStatus")
    BookCopy toBookCopy(BookCopyCreationRequest request);

    @Named("defaultStatus")
    default BookCopyStatusEnum defaultStatus(BookCopyStatusEnum status) {
        return status != null ? status : BookCopyStatusEnum.AVAILABLE;
    }

    @Mapping(target = "status", source = "status")
    void updateBookCopy(@MappingTarget BookCopy bookCopy, BookCopyUpdateRequest request);

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    BookCopyResponse toBookCopyResponse(BookCopy bookCopy);
}