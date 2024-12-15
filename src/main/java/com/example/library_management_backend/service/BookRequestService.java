package com.example.library_management_backend.service;


import com.example.library_management_backend.constants.BookRequestStatusEnum;
import com.example.library_management_backend.constants.BookRequestTypeEnum;
import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.book_request.request.BookRequestCreationRequest;
import com.example.library_management_backend.dto.book_request.request.BookRequestGetAllRequest;
import com.example.library_management_backend.dto.book_request.request.BookRequestUpdateRequest;
import com.example.library_management_backend.dto.book_request.response.BookRequestResponse;
import com.example.library_management_backend.entity.BookLoan;
import com.example.library_management_backend.entity.BookRequest;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.BookRequestMapper;
import com.example.library_management_backend.repository.BookLoanRepository;
import com.example.library_management_backend.repository.BookRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class BookRequestService {

    BookRequestRepository bookRequestRepository;
    BookLoanRepository bookLoanRepository;
    BookRequestMapper bookRequestMapper;

    public BookRequestResponse createBookRequest (BookRequestCreationRequest request) {

        BookLoan bookLoan = bookLoanRepository.findById(request.getBookLoanId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_LOAN_NOT_EXISTED));

        BookRequest bookRequest = bookRequestMapper.toBookRequest(request);
        bookRequest.setBookLoan(bookLoan);


        bookRequest = bookRequestRepository.save(bookRequest);
        return bookRequestMapper.toBookRequestResponse(bookRequest);
    }

    public void deleteBookRequest(String id) {
        BookRequest bookRequest = bookRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_REQUEST_NOT_EXISTED));

        bookRequest.setBookLoan(null);
        bookRequestRepository.delete(bookRequest);
    }

    public BookRequestResponse updateBookRequest(BookRequestUpdateRequest request) {
        BookRequest bookRequest = bookRequestRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_REQUEST_NOT_EXISTED));

        bookRequestMapper.updateBookRequest(bookRequest, request);

        bookRequestRepository.save(bookRequest);
        return bookRequestMapper.toBookRequestResponse(bookRequest);

    }
    public BookRequestResponse getBookRequestById(String id) {
        BookRequest bookRequest = bookRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_REQUEST_NOT_EXISTED));

        return bookRequestMapper.toBookRequestResponse(bookRequest);
    }

    public BaseGetAllResponse<BookRequestResponse> getAllBookRequests(BookRequestGetAllRequest request) {
        int skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        int maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String bookTitle = (request.getBookTitle() == null || request.getBookTitle().isEmpty()) ? null : request.getBookTitle();
        String userId = (request.getUserId() == null || request.getUserId().isEmpty()) ? null : request.getUserId();
        String userName = (request.getUserName() == null || request.getUserName().isEmpty()) ? null : request.getUserName();
        BookRequestStatusEnum status = request.getStatus();
        BookRequestTypeEnum type = request.getType();

        List<BookRequestResponse> bookRequestResponseList = bookRequestRepository.findAllByFilters(bookTitle, status, userId, type, userName)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(bookRequestMapper::toBookRequestResponse)
                .collect(Collectors.toList());

        return BaseGetAllResponse.<BookRequestResponse>builder()
                .data(bookRequestResponseList)
                .totalRecords(bookRequestRepository.countByFilters(bookTitle, status, userId, type, userName))
                .build();
    }

}
