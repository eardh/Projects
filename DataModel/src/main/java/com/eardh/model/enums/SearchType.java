package com.eardh.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 逻辑查询方式枚举
 * @author eardh
 * @date 2022/6/10 20:31
 */
public enum SearchType implements IEnum{

    TYPE_1(1, "*"),
    TYPE_2(2, "%");

    @EnumValue
    private final Integer code;
    private final String pattern;

    SearchType(Integer code, String pattern) {
        this.code = code;
        this.pattern = pattern;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
