package com.example.library_management_backend.repository;

import com.example.library_management_backend.constants.BookRequestStatusEnum;
import com.example.library_management_backend.constants.BookRequestTypeEnum;
import com.example.library_management_backend.entity.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest, String> {
    @Query("SELECT br " +
            "FROM BookRequest br " +
            "WHERE (:bookTitle IS NULL OR br.bookLoan.bookCopy.book.title LIKE %:bookTitle%) " +
            "AND (:type IS NULL OR br.type = :type) " +
            "AND (:userId IS NULL OR br.bookLoan.user.id = :userId) " +
            "AND (:userName IS NULL OR br.bookLoan.user.name LIKE %:userName%) " +
            "AND (:status IS NULL OR br.status = :status) " +
            "ORDER BY br.UpdatedAt DESC")
    List<BookRequest> findAllByFilters(@Param("bookTitle") String bookTitle,
                                       @Param("status") BookRequestStatusEnum status,
                                       @Param("userId") String userId,
                                       @Param("type") BookRequestTypeEnum type,
                                       @Param("userName") String userName);


    @Query("SELECT COUNT(br) " +
            "FROM BookRequest br " +
            "WHERE (:bookTitle IS NULL OR br.bookLoan.bookCopy.book.title LIKE %:bookTitle%) " +
            "AND (:type IS NULL OR br.type = :type) " +
            "AND (:userId IS NULL OR br.bookLoan.user.id = :userId) " +
            "AND (:userName IS NULL OR br.bookLoan.user.name LIKE %:userName%) " +
            "AND (:status IS NULL OR br.status = :status)")
    long countByFilters(@Param("bookTitle") String bookTitle,
                        @Param("status") BookRequestStatusEnum status,
                        @Param("userId") String userId,
                        @Param("type") BookRequestTypeEnum type,
                        @Param("userName") String userName);
}
