package com.example.library_management_backend.service;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.book.request.BookCreationRequest;
import com.example.library_management_backend.dto.book.request.BookGetAllRequest;
import com.example.library_management_backend.dto.book.request.BookUpdateRequest;
import com.example.library_management_backend.dto.book.response.BookResponse;
import com.example.library_management_backend.dto.publisher.response.PublisherResponse;
import com.example.library_management_backend.dto.user.response.UserResponse;
import com.example.library_management_backend.entity.Book;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.BookMapper;
import com.example.library_management_backend.mapper.PublisherMapper;
import com.example.library_management_backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class BookService {
    CategoryRepository categoryRepository;
    AuthorRepository authorRepository;
    PublisherRepository publisherRepository;
    BookRepository bookRepository;
    BookMapper bookMapper;
    BookCopyRepository bookCopyRepository;

    public BookResponse createBook(BookCreationRequest request) {
        Book book = bookMapper.toBook(request);
        book.setPublisher(publisherRepository.findById(String.valueOf(request.getPublisherId()))
                .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_EXISTED)));
        var authors = authorRepository.findAllById(
                request.getAuthorIds().stream().map(String::valueOf).collect(Collectors.toSet())
        );
        if (authors.size() != request.getAuthorIds().size()) {
            throw new AppException(ErrorCode.AUTHOR_NOT_EXISTED);
        }
        book.setAuthors(new HashSet<>(authors));
        var categories = categoryRepository.findAllById(
                request.getCategoryIds().stream().map(String::valueOf).collect(Collectors.toSet())
        );
        if (categories.size() != request.getCategoryIds().size()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        book.setCategories(new HashSet<>(categories));
        try {
            book = bookRepository.save(book);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.BOOK_EXISTED);
        }
        BookResponse bookResponse = bookMapper.toBookResponse(book);
        bookResponse.setPublisherId(book.getPublisher().getId());
        bookResponse.setPublisherName(book.getPublisher().getName());

        return bookResponse;
    }

    public BaseGetAllResponse<BookResponse> getAllBooks(BookGetAllRequest request) {
        int skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        int maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String title = (request.getTitle() == null || request.getTitle().isEmpty()) ? null : request.getTitle();
        Integer publisherId = (request.getPublisherId() == null || request.getPublisherId() == 0) ? null : request.getPublisherId();
        Integer authorId = (request.getAuthorId() == null || request.getAuthorId() == 0) ? null : request.getAuthorId();
        Integer categoryId = (request.getCategoryId() == null || request.getCategoryId() == 0) ? null : request.getCategoryId();

        List<BookResponse> bookResponseList = bookRepository.findAllByFilters(title, publisherId, authorId, categoryId)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(book -> {
                    BookResponse bookResponse = bookMapper.toBookResponse(book);
                    long availableCopies = bookCopyRepository.countByFilters(String.valueOf(book.getId()), null, BookCopyStatusEnum.AVAILABLE);
                    bookResponse.setNumberOfCopiesAvailable(availableCopies);
                    return bookResponse;
                })
                .collect(Collectors.toList());

        return BaseGetAllResponse.<BookResponse>builder()
                .data(bookResponseList)
                .totalRecords(bookRepository.countByFilters(title, publisherId, authorId, categoryId))
                .build();
    }

    public BookResponse updateBook(BookUpdateRequest request) {
        Book book = bookRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));
        bookMapper.updateBook(book, request);
        if (request.getPublisherId() != 0) {
            book.setPublisher(publisherRepository.findById(String.valueOf(request.getPublisherId()))
                    .orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_EXISTED)));
        }
        var authors = authorRepository.findAllById(
                request.getAuthorIds().stream().map(String::valueOf).collect(Collectors.toSet())
        );
        if (authors.size() != request.getAuthorIds().size()) {
            throw new AppException(ErrorCode.AUTHOR_NOT_EXISTED);
        }
        book.setAuthors(new HashSet<>(authors));
        var categories = categoryRepository.findAllById(
                request.getCategoryIds().stream().map(String::valueOf).collect(Collectors.toSet())
        );
        if (categories.size() != request.getCategoryIds().size()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        book.setCategories(new HashSet<>(categories));
        book = bookRepository.save(book);
        BookResponse bookResponse = bookMapper.toBookResponse(book);
        bookResponse.setPublisherId(book.getPublisher().getId());
        bookResponse.setPublisherName(book.getPublisher().getName());
        return bookResponse;
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
        BookResponse bookResponse = bookMapper.toBookResponse(book);
        bookResponse.setPublisherId(book.getPublisher().getId());
        bookResponse.setPublisherName(book.getPublisher().getName());
        return bookResponse;
    }
}