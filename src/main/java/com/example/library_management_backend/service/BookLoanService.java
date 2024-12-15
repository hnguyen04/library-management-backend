package com.example.library_management_backend.service;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import com.example.library_management_backend.constants.BookLoanStatusEnum;
import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.book_loan.request.*;
import com.example.library_management_backend.dto.book_loan.response.BookLoanResponse;
import com.example.library_management_backend.entity.BookCopy;
import com.example.library_management_backend.entity.BookLoan;
import com.example.library_management_backend.entity.User;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.BookLoanMapper;
import com.example.library_management_backend.repository.BookCopyRepository;
import com.example.library_management_backend.repository.BookLoanRepository;
import com.example.library_management_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class BookLoanService {
    BookCopyRepository bookCopyRepository;
    BookLoanRepository bookLoanRepository;
    UserRepository userRepository;
    BookLoanMapper bookLoanMapper;

    public BaseGetAllResponse<BookLoanResponse> getAllBookLoans(BookLoanGetAllRequest request) {
        int skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        int maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String userId = (request.getUserId() == null || request.getUserId().isEmpty()) ? null : request.getUserId();
        String bookTitle = (request.getBookTitle() == null || request.getBookTitle().isEmpty()) ? null : request.getBookTitle();
        BookLoanStatusEnum status = request.getStatus();

        List<BookLoanResponse> bookLoanResponseList = bookLoanRepository.findAllByFilters(userId, bookTitle, status)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(bookLoanMapper::toBookLoanResponse)
                .collect(Collectors.toList());

        return BaseGetAllResponse.<BookLoanResponse>builder()
                .data(bookLoanResponseList)
                .totalRecords(bookLoanRepository.countByFilters(userId, bookTitle, status))
                .build();
    }

    public BookLoanResponse createBookLoan(BookLoanCreationRequest request) {
        BookCopy bookCopy = bookCopyRepository.findById(request.getBookCopyId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_EXISTED));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        BookLoan bookLoan = bookLoanMapper.toBookLoan(request);
        bookLoan.setBookCopy(bookCopy);
        bookLoan.setUser(user);

        if (request.getLoanDate() != null && request.getNumberOfDaysLoan() > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(request.getLoanDate());
            calendar.add(Calendar.DAY_OF_YEAR, request.getNumberOfDaysLoan());
            bookLoan.setReturnDate(calendar.getTime());
        } else {
            throw new AppException(ErrorCode.INVALID_NUMBER_OF_DAYS_LOAN);
        }

        bookLoan = bookLoanRepository.save(bookLoan);
        return bookLoanMapper.toBookLoanResponse(bookLoan);
    }

    public BookLoanResponse updateBookLoan(BookLoanUpdateRequest request) {
        BookLoan bookLoan = bookLoanRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_LOAN_NOT_EXISTED));

        if (request.getBookCopyId() != null) {
            BookCopy bookCopy = bookCopyRepository.findById(request.getBookCopyId())
                    .orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_EXISTED));
            bookLoan.setBookCopy(bookCopy);
        }

        bookLoanMapper.updateBookLoan(bookLoan, request);

        bookLoan.setLoanDate(request.getLoanDate());
        bookLoan.setReturnDate(request.getReturnDate());
        bookLoan.setActualReturnDate(request.getActualReturnDate());

        bookLoan = bookLoanRepository.save(bookLoan);
        return bookLoanMapper.toBookLoanResponse(bookLoan);
    }

    public BookLoanResponse getBookLoanById(String id) {
        BookLoan bookLoan = bookLoanRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_LOAN_NOT_EXISTED));
        return bookLoanMapper.toBookLoanResponse(bookLoan);
    }

    public void deleteBookLoan(String id) {
        BookLoan bookLoan = bookLoanRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_LOAN_NOT_EXISTED));
        bookLoan.setBookCopy(null);
        bookLoan.setUser(null);
        bookLoanRepository.save(bookLoan);
        bookLoanRepository.deleteById(id);
    }

    public BookLoanResponse requestBorrow(BookLoanRequestBorrowRequest request) {
        BookCopy bookCopy = bookCopyRepository.findFirstByBookIdAndStatus(request.getBookId(), BookCopyStatusEnum.AVAILABLE)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_COPY_NOT_AVAILABLE));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (bookCopy.getStatus() == BookCopyStatusEnum.AVAILABLE) {
            bookCopy.setStatus(BookCopyStatusEnum.UNAVAILABLE);
            bookCopyRepository.save(bookCopy);
        }
        bookCopyRepository.save(bookCopy);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(request.getLoanDate());
        calendar.add(Calendar.DAY_OF_YEAR, request.getNumberOfDaysLoan());
        Date returnDate = calendar.getTime();

        BookLoan bookLoan = BookLoan.builder()
                .bookCopy(bookCopy)
                .user(user)
                .loanDate(request.getLoanDate())
                .returnDate(returnDate)
                .status(BookLoanStatusEnum.REQUEST_BORROWING)
                .build();

        bookLoan = bookLoanRepository.save(bookLoan);
        return bookLoanMapper.toBookLoanResponse(bookLoan);
    }

    public BookLoanResponse setBookLoanBorrowed(BookLoanSetBorrowedRequest request) {
        BookLoan bookLoan = bookLoanRepository.findById(request.getBookLoanId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_LOAN_NOT_EXISTED));

        bookLoan.setStatus(BookLoanStatusEnum.BORROWED);
        bookLoan = bookLoanRepository.save(bookLoan);

        return bookLoanMapper.toBookLoanResponse(bookLoan);
    }

    public BookLoanResponse requestReturn(BookLoanRequestReturnRequest request) {
        BookLoan bookLoan = bookLoanRepository.findById(request.getBookLoanId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_LOAN_NOT_EXISTED));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!bookLoan.getUser().getId().equals(request.getUserId())) {
            throw new AppException(ErrorCode.USER_NOT_AUTHORIZED);
        }

        bookLoan.setStatus(BookLoanStatusEnum.REQUEST_RETURNING);
        bookLoan.setActualReturnDate(new Date());
        bookLoan = bookLoanRepository.save(bookLoan);

        return bookLoanMapper.toBookLoanResponse(bookLoan);
    }
}