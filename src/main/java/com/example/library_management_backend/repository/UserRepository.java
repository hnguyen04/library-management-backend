package com.example.library_management_backend.repository;

import com.example.library_management_backend.dto.user.response.UserResponse;
import com.example.library_management_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
//    boolean existsByUsername(String username);

    Optional<User> findByName(String name);

    @Query("SELECT new com.example.library_management_backend.dto.user.response.UserResponse(" +
            "u.id, u.name, u.userName, u.email, u.CreatedAt, u.UpdatedAt, r.id, r.name) " +
            "FROM User u " +
            "LEFT JOIN u.role r " +
            "WHERE (:name IS NULL OR u.name = :name) " +
            "AND (:email IS NULL OR u.email = :email) " +
            "AND (:roleId IS NULL OR r.id = :roleId) " +
            "ORDER BY u.UpdatedAt DESC")
    List<UserResponse> findAllByFilters(@Param("name") String name,
                                        @Param("email") String email,
                                        @Param("roleId") Integer roleId);

    @Query("SELECT COUNT(u) " +
            "FROM User u " +
            "LEFT JOIN u.role r " +
            "WHERE (:name IS NULL OR u.name = :name) " +
            "AND (:email IS NULL OR u.email = :email) " +
            "AND (:roleId IS NULL OR r.id = :roleId)")
    long countByFilters(@Param("name") String name,
                        @Param("email") String email,
                        @Param("roleId") Integer roleId);
}
