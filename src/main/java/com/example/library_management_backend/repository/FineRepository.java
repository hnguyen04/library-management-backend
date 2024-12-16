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
            "WHERE (:userId IS NULL OR bl.user.id = :userId) " +
            "ORDER BY f.UpdatedAt DESC")
    List<Fine> findAllFinesByFilters(@Param("userId") String userId);

    @Query("SELECT COUNT(f) " +
            "FROM Fine f " +
            "JOIN f.bookLoan bl " +
            "WHERE (:userId IS NULL OR bl.user.id = :userId)")
    long countByFilters(@Param("userId") String userId);
}