package com.eardh.utils;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * 校验工具类
 * @author eardh
 * @date 2022/6/16 22:07
 */
public class ValidateUtil {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 通过组来校验实体类
     */
    public static <T> void validate(T t, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, groups);
        if (constraintViolations.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                validateError.append(constraintViolation.getPropertyPath().toString()).append(":").append(constraintViolation.getMessage()).append(";");
            }
            throw new ValidationException(validateError.toString());
        }
    }
}