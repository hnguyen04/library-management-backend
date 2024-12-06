package com.example.library_management_backend.repository;

import com.example.library_management_backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("SELECT c FROM Category c WHERE (:name IS NULL OR c.name LIKE %:name%)")
    List<Category> findAllByFilters(@Param("name") String name);

    @Query("SELECT COUNT(c) " +
            "FROM Category c " +
            "WHERE (:name IS NULL OR c.name LIKE %:name%)")
    long countByFilters(@Param("name") String name);
}