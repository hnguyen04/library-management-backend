package com.example.library_management_backend.mapper;

import com.example.library_management_backend.dto.fine.request.FineCreationRequest;
import com.example.library_management_backend.dto.fine.request.FineUpdateRequest;
import com.example.library_management_backend.dto.fine.response.FineResponse;
import com.example.library_management_backend.entity.Fine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FineMapper {
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "bookLoan.id", source = "bookLoanId")
    Fine toFine(FineCreationRequest request);

    void updateFine(@MappingTarget Fine fine, FineUpdateRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "bookLoan.id", target = "bookLoanId")
    @Mapping(source = "bookLoan.bookCopy.book.price", target = "bookPrice")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    FineResponse toFineResponse(Fine fine);
}