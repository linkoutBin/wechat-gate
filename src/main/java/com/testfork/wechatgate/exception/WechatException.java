package com.testfork.wechatgate.exception;

/**
 * @Author: xingshulin
 * @Date: 2018/12/27 下午1:10
 * @Description: TODO
 * @Version: 1.0
 **/
public class WechatException extends Exception {

    public WechatException() {
    }

    public WechatException(String message) {
        super(message);
    }

    public WechatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatException(Throwable cause) {
        super(cause);
    }

    public WechatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
