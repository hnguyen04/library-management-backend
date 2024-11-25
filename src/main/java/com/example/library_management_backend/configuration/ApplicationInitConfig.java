package com.example.library_management_backend.configuration;

import com.example.library_management_backend.constants.RoleEnum;
import com.example.library_management_backend.entity.Role;
import com.example.library_management_backend.entity.User;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.repository.RoleRepository;
import com.example.library_management_backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository) {
        return args -> {
            if (roleRepository.findByName(RoleEnum.ADMIN).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(RoleEnum.ADMIN)
                        .build());
            }
            if (roleRepository.findByName(RoleEnum.STAFF).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(RoleEnum.STAFF)
                        .build());
            }
            if (roleRepository.findByName(RoleEnum.USER).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(RoleEnum.USER)
                        .build());
            }
            // Create default admin user
            if (userRepository.findByName("admin").isEmpty()) {
                userRepository.save(User.builder()
                        .name("admin")
                        .userName("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@gmail.com")
                        .role(roleRepository.findByName(RoleEnum.ADMIN).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)))
                        .build());

                log.warn("Default admin user created with username: admin and password: admin");
            }

        };
    }
}
