package com.example.library_management_backend.service;

import com.example.library_management_backend.dto.book_copy.request.BookCopyCreationRequest;
import com.example.library_management_backend.dto.book_copy.request.BookCopyGetAllRequest;
import com.example.library_management_backend.dto.book_copy.request.BookCopyUpdateRequest;
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

    public List<BookCopy> getAllBookCopies(BookCopyGetAllRequest request) {
        return bookCopyRepository.findAllByFilters(request.getBookTitle(), request.getStatus())
                .stream()
                .skip(request.getSkipCount() != null ? request.getSkipCount() : 0)
                .limit(request.getMaxResultCount() != null ? request.getMaxResultCount() : 10)
                .collect(Collectors.toList());
    }

    public BookCopy updateBookCopy(String id, BookCopyUpdateRequest request) {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_EXISTED));
        bookCopyMapper.updateBookCopy(bookCopy, request);
        return bookCopyRepository.save(bookCopy);
    }

    public void deleteBookCopy(String id) {
        if (!bookCopyRepository.existsById(id)) {
            throw new AppException(ErrorCode.BOOK_COPY_NOT_EXISTED);
        }
        bookCopyRepository.deleteById(id);
    }
}