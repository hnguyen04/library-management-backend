// src/main/java/com/example/library_management_backend/repository/FineRepository.java
package com.example.library_management_backend.repository;

import com.example.library_management_backend.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, String> {
    @Query("SELECT f FROM Fine f " +
            "JOIN f.bookLoan bl " +
            "WHERE (:bookTitle IS NULL OR bl.bookCopy.book.title LIKE %:bookTitle%) " +
            "ORDER BY f.CreatedAt DESC")
    List<Fine> findAllFinesByFilters(@Param("bookTitle") String bookTitle);

    @Query("SELECT COUNT(f) " +
            "FROM Fine f " +
            "JOIN f.bookLoan bl " +
            "WHERE (:bookTitle IS NULL OR bl.bookCopy.book.title LIKE %:bookTitle%)")
    long countByFilters(@Param("bookTitle") String bookTitle);
}