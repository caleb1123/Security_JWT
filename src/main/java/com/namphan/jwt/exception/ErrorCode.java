package com.namphan.jwt.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "Tài khoản đã tồn tại", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1004, "Invalid token", HttpStatus.UNAUTHORIZED),
    ROLE_NOT_FOUND(1005, "Role not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1006, "User not found", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(1007, "Invalid password", HttpStatus.BAD_REQUEST),
    ;





    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}