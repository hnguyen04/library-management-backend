package com.example.library_management_backend.repository;

import com.example.library_management_backend.constants.BookLoanStatusEnum;
import com.example.library_management_backend.entity.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, String> {
    @Query("SELECT bl FROM BookLoan bl WHERE (:userId IS NULL OR bl.user.id = :userId) " +
            "AND (:bookTitle IS NULL OR bl.bookCopy.book.title LIKE %:bookTitle%) " +
            "AND (:status IS NULL OR bl.status = :status) " +
            "ORDER BY bl.UpdatedAt DESC")
    List<BookLoan> findAllByFilters(@Param("userId") String userId,
                                    @Param("bookTitle") String bookTitle,
                                    @Param("status") BookLoanStatusEnum status);

    @Query("SELECT COUNT(bl) FROM BookLoan bl WHERE (:userId IS NULL OR bl.user.id = :userId) " +
            "AND (:bookTitle IS NULL OR bl.bookCopy.book.title LIKE %:bookTitle%) " +
            "AND (:status IS NULL OR bl.status = :status)")
    long countByFilters(@Param("userId") String userId,
                        @Param("bookTitle") String bookTitle,
                        @Param("status") BookLoanStatusEnum status);
}