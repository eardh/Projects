package com.eardh.validate;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import com.eardh.model.enums.IEnum;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ID注解校验器
 * @author eardh
 * @date 2022/6/15 8:48
 */
public class IDValidator implements ConstraintValidator<ID, String> {

    private boolean required;
    private Set<Class<?>> groups;

    @Override
    public void initialize(ID constraintAnnotation) {
        required = constraintAnnotation.required();
        groups = Arrays.stream(constraintAnnotation.groups()).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (groups.isEmpty() || groups.contains(Crud.Create.class)) {
            if (ObjectUtil.isNull(value)) {
                return false;
            }
            return ReUtil.isMatch("^\\d{19}$", value);
        }
        return true;
    }
}
