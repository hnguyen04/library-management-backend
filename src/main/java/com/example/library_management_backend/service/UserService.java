package com.example.library_management_backend.service;

import com.example.library_management_backend.constants.Role;
import com.example.library_management_backend.dto.user.request.UserCreationRequest;
import com.example.library_management_backend.dto.user.request.UserGetAllRequest;
import com.example.library_management_backend.dto.user.request.UserUpdateRequest;
import com.example.library_management_backend.dto.user.response.UserResponse;
import com.example.library_management_backend.entity.User;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.UserMapper;
import com.example.library_management_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private final UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers(UserGetAllRequest request) {
        log.info("in getAllUsers function");

        int skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        int maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String name = (request.getName() == null || request.getName().isEmpty()) ? null : request.getName();
        String email = (request.getEmail() == null || request.getEmail().isEmpty()) ? null : request.getEmail();

        return userRepository.findAllByFilters(name, email)
                .stream()
                .map(userMapper::toUserResponse)
                .skip(skipCount)
                .limit(maxResultCount)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        userRepository.deleteById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByName(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteManyUsers (List<String> ids) {
        try {
            List<User> users = userRepository.findAllById(ids);
            if (users.size() != ids.size()) throw new AppException(ErrorCode.USER_NOT_EXISTED);

            List<String> userIds = users.stream().map(User::getId).toList();
            try {
                userRepository.deleteAll(users);
            } catch (Exception exception){
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
        } catch (Exception exception) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }

}
