package com.example.library_management_backend.repository;

import com.example.library_management_backend.constants.BookCopyStatusEnum;
import com.example.library_management_backend.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, String> {
    @Query("SELECT bc FROM BookCopy bc WHERE (:bookId IS NULL OR bc.book.id = :bookId) " +
            "AND (:bookTitle IS NULL OR bc.book.title LIKE %:bookTitle%) " +
            "AND (:status IS NULL OR bc.status = :status) " +
            "ORDER BY bc.updatedAt DESC")
    List<BookCopy> findAllByFilters(@Param("bookId") String bookId,
                                    @Param("bookTitle") String bookTitle,
                                    @Param("status") BookCopyStatusEnum status);

    @Query("SELECT COUNT(bc) FROM BookCopy bc WHERE (:bookId IS NULL OR bc.book.id = :bookId) " +
            "AND (:bookTitle IS NULL OR bc.book.title LIKE %:bookTitle%) " +
            "AND (:status IS NULL OR bc.status = :status)")
    long countByFilters(@Param("bookId") String bookId,
                        @Param("bookTitle") String bookTitle,
                        @Param("status") BookCopyStatusEnum status);

    Optional<BookCopy> findFirstByBookIdAndStatus(Integer bookId, BookCopyStatusEnum status);
}