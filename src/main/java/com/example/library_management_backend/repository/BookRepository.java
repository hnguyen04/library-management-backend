package com.example.library_management_backend.repository;

import com.example.library_management_backend.dto.book.response.BookResponse;
import com.example.library_management_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE (:title IS NULL OR b.title LIKE %:title%) " +
            "AND (:publisherId IS NULL OR b.publisher.id = :publisherId) " +
            "AND (:authorId IS NULL OR :authorId IN (SELECT a.id FROM b.authors a)) " +
            "AND (:categoryId IS NULL OR :categoryId IN (SELECT c.id FROM b.categories c)) " +
            "ORDER BY b.UpdatedAt DESC")
    List<Book> findAllByFilters(@Param("title") String title,
                                @Param("publisherId") Integer publisherId,
                                @Param("authorId") Integer authorId,
                                @Param("categoryId") Integer categoryId);

    @Query("SELECT COUNT(b) " +
            "FROM Book b " +
            "WHERE (:title IS NULL OR b.title LIKE %:title%) " +
            "AND (:publisherId IS NULL OR b.publisher.id = :publisherId) " +
            "AND (:authorId IS NULL OR :authorId IN (SELECT a.id FROM b.authors a)) " +
            "AND (:categoryId IS NULL OR :categoryId IN (SELECT c.id FROM b.categories c))")
    long countByFilters(@Param("title") String title,
                        @Param("publisherId") Integer publisherId,
                        @Param("authorId") Integer authorId,
                        @Param("categoryId") Integer categoryId);
}