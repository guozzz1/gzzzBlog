package com.gz.blog.common;


import lombok.Data;

/**
 * @Author: guozzz
 * @Date: 2024/09/14/14:27
 * @Description:
 */
@Data
public class R<T> {
    private static final long serialVersionUID = -3541610571427268456L;
    private int code;
    private T data;
    private String message;
    private String description;

    public R(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public R(int code, T data) {
        this(code, data, "", "");
    }

    public R(int code, T data, String message) {
        this(code, data, message, "");
    }

    public R(ErrorCode errorCode) {
        this(errorCode.getCode(), null,
                errorCode.getMessage(), errorCode.getDescription());
    }
    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> R<T> success(T data) {
        return new R<>(200, data, "success");
    }
    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static R error(ErrorCode errorCode) {
        return new R<>(errorCode);
    }
    /**
     * 失败
     *
     * @param code
     * @param message
     * @param description
     * @return
     */
    public static R error(int code, String message, String description) {
        return new R<>(code, null, message, description);
    }
    /**
     * 失败 - 消息和描述
     *
     * @param errorCode
     * @param message
     * @param description
     * @return
     */
    public static R error(ErrorCode errorCode, String message, String description) {
        return new R<>(errorCode.getCode(), null, message,description);
    }
    /**
     * 失败 - 描述无消息
     *
     * @param errorCode
     * @param description
     * @return
     */
    public static R error(ErrorCode errorCode, String description) {
        return new R<>(errorCode.getCode(), null,errorCode.getMessage(), description);
    }
}
