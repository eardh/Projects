package com.eardh.config.exception;

/**
 * 非法操作异常
 * @author eardh
 * @date 2022/6/16 11:15
 */
public class IllegalOprException extends RuntimeException{

    public IllegalOprException(String message) {
        super(message);
    }
}
