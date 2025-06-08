package com.sdjyyds.user.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Slf4j
public class BusinessException extends RuntimeException {

    private final int code;

    /**
     * 构造函数，使用默认错误代码400创建异常对象
     *
     * @param message 错误描述信息
     */
    public BusinessException(String message) {
        super(message);
        log.info("业务异常：{}", message);
        this.code = 400;
    }

    /**
     * 构造函数，使用指定错误代码和描述创建异常对象
     *
     * @param code    错误代码
     * @param message 错误描述信息
     */
    public BusinessException(int code, String message) {
        super(message);
        log.info("业务异常：{}", message);
        this.code = code;
    }

    /**
     * 获取错误代码
     *
     * @return 返回错误代码
     */
    public int getCode() {
        return code;
    }
}
