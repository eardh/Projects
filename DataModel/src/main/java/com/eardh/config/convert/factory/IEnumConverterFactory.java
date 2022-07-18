package com.eardh.config.convert.factory;

import com.eardh.config.convert.EnumConverter;
import com.eardh.model.enums.IEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * 枚举接口 IEnum 用于对请求参数转为枚举类的转换器工厂
 * @author eardh
 * @date 2022/6/13 15:35
 */
@Component
public class IEnumConverterFactory implements ConverterFactory<String, IEnum> {

    @NotNull
    @Override
    public <T extends IEnum> Converter<String, T> getConverter(@NotNull Class<T> targetType) {
        return new EnumConverter<>(targetType);
    }
}