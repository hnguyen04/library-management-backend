package com.example.library_management_backend.controller;


import com.example.library_management_backend.constants.BookRequestStatusEnum;
import com.example.library_management_backend.constants.BookRequestTypeEnum;
import com.example.library_management_backend.dto.base.response.ApiResponse;
import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.book_request.request.BookRequestCreationRequest;
import com.example.library_management_backend.dto.book_request.request.BookRequestGetAllRequest;
import com.example.library_management_backend.dto.book_request.request.BookRequestUpdateRequest;
import com.example.library_management_backend.dto.book_request.response.BookRequestResponse;
import com.example.library_management_backend.service.BookRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-requests")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookRequestController {
    BookRequestService bookRequestService;

    @PostMapping("/Create")
    public ApiResponse<BookRequestResponse> createBookRequest(@RequestBody BookRequestCreationRequest request) {
        return ApiResponse.<BookRequestResponse>builder()
                .result(bookRequestService.createBookRequest(request))
                .build();
    }

    @GetMapping("/GetById")
    public ApiResponse<BookRequestResponse> getBookRequestById(@RequestParam String id) {
        return ApiResponse.<BookRequestResponse>builder()
                .result(bookRequestService.getBookRequestById(id))
                .build();
    }

    @GetMapping("/GetAll")
    public ApiResponse<BaseGetAllResponse<BookRequestResponse>> getAllBookLoans(
            @RequestParam(value = "bookTitle", required = false) String bookTitle,
            @RequestParam(value = "status", required = false) BookRequestStatusEnum status,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "type", required = false) BookRequestTypeEnum type,
            @RequestParam(value = "skipCount", defaultValue = "0") int skipCount,
            @RequestParam(value = "maxResultCount", defaultValue = "10") int maxResultCount) {

        BookRequestGetAllRequest request = BookRequestGetAllRequest.builder()
                .bookTitle(bookTitle)
                .userId(userId)
                .userName(userName)
                .type(type)
                .status(status)
                .skipCount(skipCount)
                .maxResultCount(maxResultCount)
                .build();

        return ApiResponse.<BaseGetAllResponse<BookRequestResponse>>builder()
                .result(bookRequestService.getAllBookRequests(request))
                .build();
    }

    @PutMapping("/Update")
    public ApiResponse<BookRequestResponse> updateBookRequest(@RequestBody BookRequestUpdateRequest request) {
        return ApiResponse.<BookRequestResponse>builder()
                .result(bookRequestService.updateBookRequest(request))
                .build();
    }

    @DeleteMapping("/Delete")
    public ApiResponse<String> deleteBookRequest(@RequestParam String id) {
        bookRequestService.deleteBookRequest(id);
        return ApiResponse.<String>builder()
                .result("Book request deleted successfully")
                .build();
    }
}
