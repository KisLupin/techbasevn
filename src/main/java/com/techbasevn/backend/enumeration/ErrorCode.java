package com.techbasevn.backend.enumeration;

public enum ErrorCode {
    USERNAME_NOT_EXIST("error.api.username_not_exist", "Username is not existed"),
    USERNAME_EXISTED("error.api.username_existed", "Username is existed"),
    USER_NOT_EXIST("error.api.user_not_exist", "User is not existed"),
    LOGIN_REQUEST_NOT_VALID("error.api.login_request_not_valid", "User is not existed"),
    PASSWORD_INVALID("error.api.password_invalid", ""),
    REQUEST_CAN_NOT_BE_NULL("error.api.request_can_not_be_null", ""),
    POSITION_NOT_EXIST("error.api.position_not_exist", ""),
    TIME_FORMAT_INVALID("error.api.time_format_invalid", ""),
    TOKEN_NOT_EXIST("error.api.token_not_exist", "Token is not existed");;

    private final String errorCode;
    private final String message;

    ErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

}
