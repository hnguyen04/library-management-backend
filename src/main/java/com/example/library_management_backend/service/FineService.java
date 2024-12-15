package com.example.library_management_backend.service;

import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.fine.request.FineCreationRequest;
import com.example.library_management_backend.dto.fine.request.FineGetAllRequest;
import com.example.library_management_backend.dto.fine.request.FineUpdateRequest;
import com.example.library_management_backend.dto.fine.response.FineResponse;
import com.example.library_management_backend.entity.BookLoan;
import com.example.library_management_backend.entity.Fine;
import com.example.library_management_backend.entity.User;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.FineMapper;
import com.example.library_management_backend.repository.BookLoanRepository;
import com.example.library_management_backend.repository.FineRepository;
import com.example.library_management_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class FineService {
    FineRepository fineRepository;
    UserRepository userRepository;
    BookLoanRepository bookLoanRepository;
    FineMapper fineMapper;

    public BaseGetAllResponse<FineResponse> getAllFines(FineGetAllRequest request) {
        int skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        int maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String userId = (request.getUserId() == null || request.getUserId().isEmpty()) ? null : request.getUserId();

        List<FineResponse> fineResponseList = fineRepository.findAllFinesByFilters(userId)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .map(fineMapper::toFineResponse)
                .collect(Collectors.toList());

        long totalRecords = fineRepository.countByFilters(userId);

        return BaseGetAllResponse.<FineResponse>builder()
                .data(fineResponseList)
                .totalRecords(totalRecords)
                .build();
    }

    public FineResponse createFine(FineCreationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        BookLoan bookLoan = bookLoanRepository.findById(request.getBookLoanId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_LOAN_NOT_EXISTED));

        double amount;
        switch (bookLoan.getStatus()) {
            case RETURNED:
                if (bookLoan.getActualReturnDate() != null && bookLoan.getActualReturnDate().after(bookLoan.getReturnDate())) {
                    long diffInMillies = Math.abs(bookLoan.getActualReturnDate().getTime() - bookLoan.getReturnDate().getTime());
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    amount = diff * 0.01 * bookLoan.getBookCopy().getBook().getPrice();
                } else {
                    throw new AppException(ErrorCode.INVALID_BOOK_LOAN_STATUS);
                }
                break;
            case NONRETURNABLE:
                amount = bookLoan.getBookCopy().getBook().getPrice();
                break;
            default:
                throw new AppException(ErrorCode.INVALID_BOOK_LOAN_STATUS);
        }

        Fine fine = fineMapper.toFine(request);
        fine.setUser(user);
        fine.setBookLoan(bookLoan);
        fine.setAmount(amount);

        fine = fineRepository.save(fine);
        return fineMapper.toFineResponse(fine);
    }

    public FineResponse updateFine(FineUpdateRequest request) {
        Fine fine = fineRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.FINE_NOT_EXISTED));

        fineMapper.updateFine(fine, request);
        fine.setAmount(request.getAmount());
        fine = fineRepository.save(fine);
        return fineMapper.toFineResponse(fine);
    }

    public FineResponse getFineById(String id) {
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FINE_NOT_EXISTED));
        return fineMapper.toFineResponse(fine);
    }

    public void deleteFine(String id) {
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FINE_NOT_EXISTED));
        fine.setBookLoan(null);
        fine.setUser(null);
        fineRepository.save(fine);
        fineRepository.deleteById(id);
    }
}