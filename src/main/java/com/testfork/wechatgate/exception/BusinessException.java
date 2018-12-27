package com.testfork.wechatgate.exception;

/**
 * @Author: xingshulin
 * @Date: 2018/12/27 下午2:20
 * @Description: 业务异常类
 * @Version: 1.0
 **/
public class BusinessException extends Exception {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
