package com.example.library_management_backend.repository;

import com.example.library_management_backend.constants.RoleEnum;
import com.example.library_management_backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(RoleEnum name);
}
