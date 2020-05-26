package com.bysj.common.exception;

/**
 * 自定义业务异常
 */
public class BizException extends RuntimeException {

    public BizException(String message) {
        super(message);
    }
}
