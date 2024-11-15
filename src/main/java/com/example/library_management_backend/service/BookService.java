package com.example.library_management_backend.service;

import com.example.library_management_backend.dto.book.request.BookCreationRequest;
import com.example.library_management_backend.dto.book.request.BookGetAllRequest;
import com.example.library_management_backend.dto.book.request.BookUpdateRequest;
import com.example.library_management_backend.dto.book.response.BookResponse;
import com.example.library_management_backend.entity.Book;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.BookMapper;
import com.example.library_management_backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class BookService {
    BookRepository bookRepository;
    BookMapper bookMapper;

    public BookResponse createBook(BookCreationRequest request) {
        Book book = bookMapper.toBook(request);
        try {
            book = bookRepository.save(book);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.BOOK_EXISTED);
        }
        return bookMapper.toBookResponse(book);
    }

    public List<BookResponse> getAllBooks(BookGetAllRequest request) {
        return bookRepository.findAllByFilters(request.getTitle())
                .stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
    }

    public BookResponse updateBook(int id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));
        bookMapper.updateBook(book, request);
        book = bookRepository.save(book);
        return bookMapper.toBookResponse(book);
    }

    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new AppException(ErrorCode.BOOK_NOT_EXISTED);
        }
        bookRepository.deleteById(id);
    }

    public BookResponse getBookById(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));
        return bookMapper.toBookResponse(book);
    }
}