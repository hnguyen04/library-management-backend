package com.example.library_management_backend.configuration;

import com.example.library_management_backend.constants.Role;
import com.example.library_management_backend.entity.User;
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

    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository) {
        return args -> {
            // Create default admin user
            if (userRepository.findByName("admin").isEmpty()) {
                userRepository.save(User.builder()
                        .name("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@gmail.com")
                        .role(Role.ADMIN)
                        .build());

                log.warn("Default admin user created with username: admin and password: admin");
            }

        };
    }
}
