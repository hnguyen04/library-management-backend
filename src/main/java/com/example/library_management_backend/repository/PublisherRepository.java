package com.example.library_management_backend.repository;
import com.example.library_management_backend.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, String> {
    @Query("SELECT p FROM Publisher p WHERE (:name IS NULL OR p.name LIKE %:name%)")
    List<Publisher> findAllByFilters(@Param("name") String name);

    @Query("SELECT COUNT(p) " +
            "FROM Publisher p " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%)")
    long countByFilters(@Param("name") String name);
}
