package com.eardh.config.exception;

/**
 * 数据操作异常
 * @author eardh
 * @date 2022/6/16 13:08
 */
public class DataOprException  extends RuntimeException{

    public DataOprException(String message) {
        super(message);
    }
}