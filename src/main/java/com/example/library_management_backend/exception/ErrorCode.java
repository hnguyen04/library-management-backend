package com.example.library_management_backend.exception;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1009, "Permission existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(1010, "Permission not existed", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(1011, "Role existed", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1012, "Role not existed", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(1013,"Category existed" , HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(1014,"Category not existed" , HttpStatus.NOT_FOUND),
    PUBLISHER_EXISTED(1015,"Publisher existed" , HttpStatus.BAD_REQUEST),
    PUBLISHER_NOT_EXISTED(1016,"Publisher not existed" , HttpStatus.NOT_FOUND),
    AUTHOR_EXISTED(1017,"Author existed" , HttpStatus.BAD_REQUEST),
    AUTHOR_NOT_EXISTED(1018,"Author not existed" , HttpStatus.NOT_FOUND),
    BOOK_EXISTED(1019,"Book existed" , HttpStatus.BAD_REQUEST),
    BOOK_NOT_EXISTED(1020,"Book not existed" , HttpStatus.NOT_FOUND),
    BOOK_COPY_NOT_EXISTED(1021,"BookCopy not existed" , HttpStatus.NOT_FOUND),
    WRONG_PASSWORD(1100,"Wrong password" , HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
