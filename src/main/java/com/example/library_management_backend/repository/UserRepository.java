package com.example.library_management_backend.repository;

import com.example.library_management_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
//    boolean existsByUsername(String username);
//
    Optional<User> findByName(String name);


    @Query(value = "SELECT * FROM public.users " +
            "WHERE (:name IS NULL OR name = :name) " +
            "AND (:email IS NULL OR email = :email) " +
            "ORDER BY created_at DESC"
            , nativeQuery = true)
    List<User> findAllByFilters(@Param("name") String name,
                                @Param("email") String email);
}
