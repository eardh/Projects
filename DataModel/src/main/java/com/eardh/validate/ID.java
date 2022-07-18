package com.eardh.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义ID校验注解
 * @author eardh
 * @date 2022/6/15 8:48
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IDValidator.class})
public @interface ID {

    boolean required() default true;

    String message() default "ID格式错误";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
