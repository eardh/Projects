package com.eardh.config.convert;

import com.eardh.model.enums.IEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

/**
 * 枚举接口 IEnum 用于对请求参数转为枚举类的转换器工厂
 * @author eardh
 * @date 2022/6/13 15:35
 */
public class EnumConverter<T extends IEnum> implements Converter<String, T> {

    private final Class<T> targetType;

    public EnumConverter(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public T convert(@NotNull String source) {
        for (T e : targetType.getEnumConstants()) {
            if (e.toString().equals(source) || e.getCode().toString().equals(source)) {
                return e;
            }
        }
        throw new IllegalArgumentException("枚举 Code 不正确");
    }
}