package com.eardh.model.enums;

/**
 * 对字段的四种模型属性隔离后，用于判断进行的是对哪种模型属性的操作
 * @author eardh
 * @date 2022/6/13 16:39
 */
public enum AttrOprType implements IEnum {

    CDM(1, "概念模型属性"),
    LDM(2, "逻辑模型属性"),
    PDM(3, "物理模型属性"),
    OOM(4, "面向对象模型属性");

    private final Integer code;
    private final String desc;

    AttrOprType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
