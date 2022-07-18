package com.eardh.model.enums;

/**
 * 接口类型枚举
 * @author eardh
 * @date 2022/6/13 15:17
 */
public enum InterFaceType implements IEnum{

    CREATE(1, "创建接口"),
    UPDATE(2, "更新接口"),
    DELETE(3, "删除接口"),
    QUERY(4, "查询接口"),
    PROGRESS_CHANGE(5, "进度变更接口");

    private final Integer code;
    private final String desc;

    InterFaceType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
