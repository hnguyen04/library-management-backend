package com.example.library_management_backend.repository;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import com.example.library_management_backend.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, String> {
    @Query("SELECT bc FROM BookCopy bc WHERE (:bookTitle IS NULL OR bc.book.title LIKE %:bookTitle%) " +
            "AND (:status IS NULL OR bc.status = :status)")
    List<BookCopy> findAllByFilters(@Param("bookTitle") String bookTitle,
                                    @Param("status") BookCopyStatusEnum status);
}