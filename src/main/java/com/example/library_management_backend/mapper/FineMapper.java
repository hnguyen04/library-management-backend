// src/main/java/com/example/library_management_backend/mapper/FineMapper.java
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

    @Mapping(target = "amount", source = "amount")
    void updateFine(@MappingTarget Fine fine, FineUpdateRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "bookLoan.id", target = "bookLoanId")
    FineResponse toFineResponse(Fine fine);
}