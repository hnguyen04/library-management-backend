package com.example.library_management_backend.controller;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import com.example.library_management_backend.dto.ApiResponse;
import com.example.library_management_backend.dto.book_copy.request.BookCopyCreationRequest;
import com.example.library_management_backend.dto.book_copy.request.BookCopyGetAllRequest;
import com.example.library_management_backend.dto.book_copy.request.BookCopyUpdateRequest;
import com.example.library_management_backend.dto.book_copy.response.BookCopyResponse;
import com.example.library_management_backend.entity.BookCopy;
import com.example.library_management_backend.mapper.BookCopyMapper;
import com.example.library_management_backend.service.BookCopyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book-copies")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookCopyController {
    BookCopyService bookCopyService;
    BookCopyMapper bookCopyMapper;

    @PostMapping("/Create")
    ApiResponse<BookCopyResponse> createBookCopy(@RequestBody BookCopyCreationRequest request) {
        BookCopy bookCopy = bookCopyService.createBookCopy(request);
        return ApiResponse.<BookCopyResponse>builder()
                .result(bookCopyMapper.toBookCopyResponse(bookCopy))
                .build();
    }

    @GetMapping("/GetAll")
    ApiResponse<List<BookCopyResponse>> getAllBookCopies(
            @RequestParam(value = "bookTitle", required = false) String bookTitle,
            @RequestParam(value = "status", required = false) BookCopyStatusEnum status,
            @RequestParam(value = "skipCount", defaultValue = "0") int skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") int maxResultCount) {

        BookCopyGetAllRequest request = BookCopyGetAllRequest.builder()
                .bookTitle(bookTitle)
                .status(status)
                .skipCount(skipCount)
                .maxResultCount(maxResultCount)
                .build();

        List<BookCopyResponse> responses = bookCopyService.getAllBookCopies(request)
                .stream()
                .map(bookCopyMapper::toBookCopyResponse)
                .collect(Collectors.toList());

        return ApiResponse.<List<BookCopyResponse>>builder()
                .result(responses)
                .build();
    }

    @PutMapping("/Update")
    ApiResponse<BookCopyResponse> updateBookCopy(@RequestParam String id, @RequestBody BookCopyUpdateRequest request) {
        BookCopy bookCopy = bookCopyService.updateBookCopy(id, request);
        return ApiResponse.<BookCopyResponse>builder()
                .result(bookCopyMapper.toBookCopyResponse(bookCopy))
                .build();
    }

    @DeleteMapping("/Delete")
    ApiResponse<String> deleteBookCopy(@RequestParam String id) {
        bookCopyService.deleteBookCopy(id);
        return ApiResponse.<String>builder()
                .result("Book copy deleted successfully")
                .build();
    }
}