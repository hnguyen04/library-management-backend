package com.example.library_management_backend.controller;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import com.example.library_management_backend.constants.BookLoanStatusEnum;
import com.example.library_management_backend.dto.base.response.ApiResponse;
import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.book_loan.request.*;
import com.example.library_management_backend.dto.book_loan.response.BookLoanResponse;
import com.example.library_management_backend.entity.BookCopy;
import com.example.library_management_backend.service.BookLoanService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/book-loans")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookLoanController {
    BookLoanService bookLoanService;

    @PostMapping("/Create")
    public ApiResponse<BookLoanResponse> createBookLoan(@RequestBody BookLoanCreationRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.createBookLoan(request))
                .build();
    }

    @GetMapping("/GetAll")
    public ApiResponse<BaseGetAllResponse<BookLoanResponse>> getAllBookLoans(
            @RequestParam(value = "bookTitle", required = false) String bookTitle,
            @RequestParam(value = "status", required = false) BookLoanStatusEnum status,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "skipCount", defaultValue = "0") int skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") int maxResultCount) {

        BookLoanGetAllRequest request = BookLoanGetAllRequest.builder()
                .bookTitle(bookTitle)
                .status(status)
                .userId(userId)
                .skipCount(skipCount)
                .maxResultCount(maxResultCount)
                .build();

        return ApiResponse.<BaseGetAllResponse<BookLoanResponse>>builder()
                .result(bookLoanService.getAllBookLoans(request))
                .build();
    }

    @GetMapping("/GetById")
    public ApiResponse<BookLoanResponse> getBookLoanById(@RequestParam String id) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.getBookLoanById(id))
                .build();
    }

    @DeleteMapping("/Delete")
    public ApiResponse<String> deleteBookLoan(@RequestParam String id) {
        bookLoanService.deleteBookLoan(id);
        return ApiResponse.<String>builder()
                .result("Book loan deleted successfully")
                .build();
    }

    @PutMapping("/Update")
    public ApiResponse<BookLoanResponse> updateBookLoan(@RequestBody BookLoanUpdateRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.updateBookLoan(request))
                .build();
    }

    @PostMapping("/RequestBorrow")
    public ResponseEntity<BookLoanResponse> requestBorrow(@Valid @RequestBody BookLoanRequestBorrowRequest request) {
        BookLoanResponse response = bookLoanService.requestBorrow(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/SetBorrowed")
    public ApiResponse<BookLoanResponse> setBookLoanBorrowed(@RequestBody BookLoanSetBorrowedRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.setBookLoanBorrowed(request))
                .build();
    }

    @PostMapping("/RequestReturn")
    public ApiResponse<BookLoanResponse> requestReturn(@RequestBody BookLoanRequestReturnRequest request) {
        return ApiResponse.<BookLoanResponse>builder()
                .result(bookLoanService.requestReturn(request))
                .build();
    }
}