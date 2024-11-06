package com.example.library_management_backend.mapper;

import com.example.library_management_backend.dto.category.request.CategoryCreationRequest;
import com.example.library_management_backend.dto.category.request.CategoryUpdateRequest;
import com.example.library_management_backend.dto.category.response.CategoryResponse;
import com.example.library_management_backend.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryCreationRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest request);
}