package com.example.library_management_backend.service;

import com.example.library_management_backend.dto.base.response.BaseGetAllResponse;
import com.example.library_management_backend.dto.user.request.*;
import com.example.library_management_backend.dto.user.response.UserConfigurationResponse;
import com.example.library_management_backend.dto.user.response.UserResponse;
import com.example.library_management_backend.entity.Permission;
import com.example.library_management_backend.entity.User;
import com.example.library_management_backend.exception.AppException;
import com.example.library_management_backend.exception.ErrorCode;
import com.example.library_management_backend.mapper.UserMapper;
import com.example.library_management_backend.repository.PermissionRepository;
import com.example.library_management_backend.repository.RoleRepository;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;
    private final UserMapper userMapper;
    PasswordEncoder passwordEncoder;

//    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(roleRepository.findById(String.valueOf(request.getRoleId())).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setRoleName(user.getRole().getName());
        userResponse.setRoleId(user.getRole().getId());
        return userResponse;
    }

    @PreAuthorize("hasAuthority('Role.GetAll')")
    public BaseGetAllResponse<UserResponse> getAllUsers(UserGetAllRequest request) {
        log.info("in getAllUsers function");

        int skipCount = request.getSkipCount() != null ? request.getSkipCount() : 0;
        int maxResultCount = request.getMaxResultCount() != null ? request.getMaxResultCount() : 10;
        String name = (request.getName() == null || request.getName().isEmpty()) ? null : request.getName();
        String email = (request.getEmail() == null || request.getEmail().isEmpty()) ? null : request.getEmail();
        Integer roleId = request.getRoleId() != null ? request.getRoleId() : null;

        List<UserResponse> userResponseList = userRepository.findAllByFilters(name, email, roleId)
                .stream()
                .skip(skipCount)
                .limit(maxResultCount)
                .collect(Collectors.toList());

        return BaseGetAllResponse.<UserResponse>builder()
                .data(userResponseList)
                .totalRecords(userRepository.countByFilters(name, email, roleId))
                .build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(UserUpdateRequest request) {
        User user = userRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        user = userRepository.save(user);

        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setRoleName(user.getRole().getName());
        userResponse.setRoleId(user.getRole().getId());
        return userResponse;
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
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setRoleName(user.getRole().getName());
        userResponse.setRoleId(user.getRole().getId());
        return userResponse;
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByName(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setRoleName(user.getRole().getName());
        userResponse.setRoleId(user.getRole().getId());
        return userResponse;
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

    public void changePassword(ChangePasswordRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByName(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public UserConfigurationResponse getAllConfigurations() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByName(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserConfigurationResponse configResponse = new UserConfigurationResponse();

        List<Permission> allPermissionsList = permissionRepository.findAll();

        Set<Permission> grantedPermissionsSet = user.getRole().getPermissions();

        Map<String, Boolean> allPermissions = allPermissionsList.stream()
                .collect(Collectors.toMap(
                        Permission::getName,
                        permission -> grantedPermissionsSet.contains(permission) // Nếu tồn tại trong granted thì là true
                ));

        Map<String, Boolean> grantedPermissions = grantedPermissionsSet.stream()
                .collect(Collectors.toMap(Permission::getName, permission -> true));

        UserConfigurationResponse.AuthPermissions authPermissions = UserConfigurationResponse.AuthPermissions.builder()
                .allPermissions(allPermissions)
                .grantedPermissions(grantedPermissions)
                .build();

        configResponse.setRoleName(user.getRole().getName());
        configResponse.setRoleId(user.getRole().getId());
        configResponse.setAuth(authPermissions);

        return configResponse;
    }

    public void resetPassword(ResetPasswordRequest request) {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User admin = userRepository.findByName(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!passwordEncoder.matches(request.getAdminPassword(), admin.getPassword())) {
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

}
