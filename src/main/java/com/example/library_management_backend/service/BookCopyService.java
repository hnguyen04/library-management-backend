package com.example.library_management_backend.service;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.book_copy.request.BookCopyCreationRequest;
import com.example.library_management_backend.dto.book_copy.request.BookCopyGetAllRequest;
import com.example.library_management_backend.dto.book_copy.request.BookCopyUpdateRequest;
import com.example.library_management_backend.dto.book_copy.response.BookCopyResponse;
import com.example.library_management_backend.entity.BookCopy;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.BookCopyMapper;
import com.example.library_management_backend.repository.BookCopyRepository;
import com.example.library_management_backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookCopyService {
    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;
    private final BookCopyMapper bookCopyMapper;

    public BookCopy createBookCopy(BookCopyCreationRequest request) {
        BookCopy bookCopy = bookCopyMapper.toBookCopy(request);
        bookCopy.setBook(bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED)));
        return bookCopyRepository.save(bookCopy);
    }

    public BaseGetAllResponse<BookCopyResponse> getAllBookCopies(BookCopyGetAllRequest request) {
        int skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        int maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String bookTitle = (request.getBookTitle() == null || request.getBookTitle().isEmpty()) ? null : request.getBookTitle();
        BookCopyStatusEnum status = request.getStatus();

        List<BookCopyResponse> bookCopyResponseList = bookCopyRepository.findAllByFilters(bookTitle, status)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(bookCopyMapper::toBookCopyResponse)
                .collect(Collectors.toList());

        return BaseGetAllResponse.<BookCopyResponse>builder()
                .data(bookCopyResponseList)
                .totalRecords(bookCopyRepository.countByFilters(bookTitle, status))
                .build();
    }

    public BookCopyResponse getBookCopyById(String id) {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_EXISTED));
        return bookCopyMapper.toBookCopyResponse(bookCopy);
    }

    public BookCopyResponse updateBookCopy(BookCopyUpdateRequest request) {
        BookCopy bookCopy = bookCopyRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_EXISTED));
        bookCopyMapper.updateBookCopy(bookCopy, request);
        bookCopy = bookCopyRepository.save(bookCopy);
        return bookCopyMapper.toBookCopyResponse(bookCopy);
    }

    public void deleteBookCopy(String id) {
        if (!bookCopyRepository.existsById(id)) {
            throw new AppException(ErrorCode.BOOK_COPY_NOT_EXISTED);
        }
        bookCopyRepository.deleteById(id);
    }
}