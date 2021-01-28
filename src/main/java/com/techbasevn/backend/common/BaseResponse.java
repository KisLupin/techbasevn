package com.techbasevn.backend.common;

import com.techbasevn.backend.enumeration.ErrorCode;
import com.techbasevn.backend.exception.RestApiException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse {
    private Object data;
    private String errorCode;
    private String message;

    public BaseResponse(Object data) {
        super();
        this.data = data;
    }

    public BaseResponse(RestApiException ex) {
        super();
        this.errorCode = ex.getErrorCode();
        this.message = ex.getDefaultMessage();
        this.data = ex.getData();
    }

    public BaseResponse(Exception ex, ErrorCode errorCode) {
        super();
        this.errorCode = errorCode.getErrorCode();
        this.message = ex.getMessage();
    }
}
