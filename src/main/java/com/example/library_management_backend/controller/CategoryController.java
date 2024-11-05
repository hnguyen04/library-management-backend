package com.example.library_management_backend.controller;

import com.example.library_management_backend.dto.ApiResponse;
import com.example.library_management_backend.dto.category.request.CategoryCreationRequest;
import com.example.library_management_backend.dto.category.request.CategoryUpdateRequest;
import com.example.library_management_backend.dto.category.response.CategoryResponse;
import com.example.library_management_backend.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/Create")
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryCreationRequest request) {
        return ApiResponse.<CategoryResponse>builder().
                result(categoryService.createCategory(request)).
                build();
    }

    @GetMapping("/GetAll")
    ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder().
                result(categoryService.getAllCategories()).
                build();
    }

    @GetMapping("/GetById")
    ApiResponse<CategoryResponse> getCategoryById(@RequestParam String id) {
        return ApiResponse.<CategoryResponse>builder().
                result(categoryService.getCategory(id)).
                build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deleteCategory(@RequestParam String id) {
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder().
                result("Category deleted successfully").
                build();
    }

    @PutMapping("/Update")
    ApiResponse<CategoryResponse> updateCategory(@RequestParam String id, @RequestBody CategoryUpdateRequest request) {
        return ApiResponse.<CategoryResponse>builder().
                result(categoryService.updateCategory(id, request)).
                build();
    }
}