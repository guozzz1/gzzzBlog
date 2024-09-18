package com.gz.blog.exception;

import com.gz.blog.common.R;
import com.gz.blog.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: guozzz
 * @Date: 2024/09/14/13:50
 * @Description:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获BusinessException异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public R businessExceptionHandler(BusinessException e) {
        // 日志集中处理
        log.error("businessException:" + e.getMessage(), e);
        return R.error(e.getCode(), e.getMessage(),
                e.getDescription());
    }

    /**
     * 捕获RuntimeException异常
     *
     * @param e 异常
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public R runtimeExceptionHandler(BusinessException e) {
        // 日志集中处理
        log.error("runtimeException:", e);
        return R.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
