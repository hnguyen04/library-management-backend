package com.example.library_management_backend.repository;

import com.example.library_management_backend.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    @Query("SELECT c FROM Author c WHERE (:name IS NULL OR c.name LIKE %:name%)")
    List<Author> findAllByFilters(@Param("name") String name);

    @Query("SELECT COUNT(a) " +
            "FROM Author a " +
            "WHERE (:name IS NULL OR a.name LIKE %:name%)")
    long countByFilters(@Param("name") String name);
}
