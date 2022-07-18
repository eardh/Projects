package com.eardh.config;

import cn.hutool.core.convert.ConvertException;
import com.eardh.config.exception.DataOprException;
import com.eardh.config.exception.IllegalOprException;
import com.eardh.model.Answer;
import com.eardh.model.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * 全局异常处理
 *
 * @author eardh
 * @date 2022/6/14 22:08
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理其他所有异常
     * @param e {@link Exception}
     * @return 响应
     */
    @ExceptionHandler(Exception.class)
    public Answer handleException(Exception e) {
        log.error(e.getMessage());
        return Answer.fail(ResultCode.fail, e.getMessage());
    }

    /**
     * 处理参数校验错误
     * @param e {@link Exception}
     * @return 响应
     */
    @ExceptionHandler({ValidationException.class, ConvertException.class})
    public Answer handleValidationException(Exception e) {
        log.error(e.getMessage());
        return Answer.fail(ResultCode.invalid_argument, e.getMessage());
    }

    /**
     * 处理参数不匹配异常
     * @param e {@link MethodArgumentTypeMismatchException}
     * @return 响应
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Answer handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info(e.getMessage());
        return Answer.fail(ResultCode.invalid_argument, e.getName() + ": " + e.getCause().getCause().getMessage());
    }

    /**
     * 非法操作
     * @param e {@link IllegalOprException}
     * @return 响应
     */
    @ExceptionHandler(IllegalOprException.class)
    public Answer handleValidationException(IllegalOprException e) {
        log.error(e.getMessage());
        return Answer.fail(ResultCode.permission_denied, e.getMessage());
    }

    /**
     * 数据持久化操作异常
     * @param e {@link DataOprException}
     * @return 响应
     */
    @ExceptionHandler(DataOprException.class)
    public Answer handleDataOprException(DataOprException e) {
        log.error(e.getMessage());
        return Answer.fail(e.getMessage());
    }

    /**
     * 处理方法参数校验异常
     * @param e {@link MethodArgumentNotValidException}
     * @return 响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Answer handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        StringBuilder builder = new StringBuilder();
        for (ObjectError allError : e.getAllErrors()) {
            if (allError instanceof FieldError) {
                builder.append(((FieldError) allError).getField());
            } else {
                builder.append(allError.getObjectName());
            }
            builder.append(": ")
                    .append(allError.getDefaultMessage())
                    .append(";");
        }
        return Answer.fail(ResultCode.invalid_argument, builder.toString());
    }
}
