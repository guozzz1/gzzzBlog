package com.gz.blog.exception;

import com.gz.blog.common.ErrorCode;

/**
 * @Author: guozzz
 * @Date: 2024/09/14/13:49
 * @Description:
 */
public class BusinessException extends RuntimeException {
    private final int code;
    private final String description;

    //给原有的RuntimeException中加入 message code description
    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    //给原有的RuntimeException中加入errorCode
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }


    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}