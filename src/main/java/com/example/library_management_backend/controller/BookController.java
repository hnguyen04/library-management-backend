package com.example.library_management_backend.controller;

import com.example.library_management_backend.dto.base.response.ApiResponse;
import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.book.request.BookCreationRequest;
import com.example.library_management_backend.dto.book.request.BookGetAllRequest;
import com.example.library_management_backend.dto.book.request.BookUpdateRequest;
import com.example.library_management_backend.dto.book.response.BookResponse;
import com.example.library_management_backend.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    BookService bookService;

    @PostMapping("/Create")
    ApiResponse<BookResponse> createBook(@RequestBody BookCreationRequest request) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.createBook(request))
                .build();
    }

    @GetMapping("/GetAll")
    ApiResponse<BaseGetAllResponse<BookResponse>> getAllBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "publisherId", required = false) Integer publisherId,
            @RequestParam(value = "authorId", required = false) Integer authorId,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "skipCount", defaultValue = "0") int skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") int maxResultCount) {

        BookGetAllRequest request = new BookGetAllRequest();
        request.setTitle(title);
        request.setPublisherId(publisherId);
        request.setAuthorId(authorId);
        request.setCategoryId(categoryId);
        request.setSkipCount(skipCount);
        request.setMaxResultCount(maxResultCount);

        return ApiResponse.<BaseGetAllResponse<BookResponse>>builder()
                .result(bookService.getAllBooks(request))
                .build();
    }

    @GetMapping("/GetById")
    ApiResponse<BookResponse> getBookById(@RequestParam int id) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBookById(id))
                .build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deleteBook(@RequestParam int id) {
        bookService.deleteBook(id);
        return ApiResponse.<String>builder()
                .result("Book deleted successfully")
                .build();
    }

    @PutMapping("/Update")
    ApiResponse<BookResponse> updateBook(@RequestBody BookUpdateRequest request) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.updateBook(request))
                .build();
    }
}