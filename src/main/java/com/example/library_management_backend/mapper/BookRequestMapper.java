package com.example.library_management_backend.mapper;


import com.example.library_management_backend.dto.book_request.request.BookRequestCreationRequest;
import com.example.library_management_backend.dto.book_request.request.BookRequestUpdateRequest;
import com.example.library_management_backend.dto.book_request.response.BookRequestResponse;
import com.example.library_management_backend.entity.BookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookRequestMapper {
    @Mapping(target = "bookLoan.id", source = "bookLoanId")
    BookRequest toBookRequest(BookRequestCreationRequest request);

    void updateBookRequest(@MappingTarget BookRequest bookRequest, BookRequestUpdateRequest request);

    @Mapping(source = "bookLoan.bookCopy.book.title", target = "bookTitle")
    @Mapping(source = "bookLoan.bookCopy.id", target = "bookCopyId")
    @Mapping(source = "bookLoan.user.id", target = "userId")
    @Mapping(source = "bookLoan.user.name", target = "userName")
    BookRequestResponse toBookRequestResponse(BookRequest bookRequest);
}
