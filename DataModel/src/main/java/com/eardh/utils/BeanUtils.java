package com.eardh.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.TypeConverter;
import cn.hutool.core.util.ObjectUtil;
import com.eardh.model.enums.IEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单操作Bean工具的封装
 * @author eardh
 * @date 2022/6/13 22:07
 */
public class BeanUtils {

    /**
     * 将Map转换为Bean对象
     * @param clazz Bean Class
     * @param map {@link Map}
     * @param <T> Bean类型
     * @return Bean
     */
    public static <T> T mapToBean(Class<T> clazz, Map<String, Object> map) {
        TypeConverter converter = (targetType, value) -> {
            Class<?> cl = (Class<?>) targetType;
            if(IEnum.class.isAssignableFrom(cl)) {
                for (Object e : cl.getEnumConstants()) {
                    if (e instanceof IEnum) {
                        if (e.equals(value) || ((IEnum) e).getCode().toString().equals(value)) {
                            return e;
                        }
                    }
                }
            }
            return value;
        };
        CopyOptions copyOptions = CopyOptions.create()
                .setConverter(converter);
        return BeanUtil.mapToBean(map, clazz, false, copyOptions);
    }

    /**
     * 将Bean转为Map对象
     * @param t bean
     * @param <T> Bean类型
     * @return {@link Map}
     */
    public static <T> Map<String, Object> beanToMap(T t) {
        TypeConverter converter = (targetType, value) -> ObjectUtil.isNotNull(value) ? value.toString() : value;
        Map<String, Object> map = new ConcurrentHashMap<>();
        CopyOptions copyOptions = CopyOptions.create()
                .ignoreNullValue()
                .setConverter(converter);
        BeanUtil.beanToMap(t, map, copyOptions);
        return map;
    }

}
