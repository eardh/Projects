package com.eardh.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 数据类型枚举
 * @author eardh
 * @date 2022/6/13 15:35
 */
public enum DataType implements IEnum{

    TINYINT(1, "TINYINT"),
    SMALLINT(2, "SMALLINT"),
    MEDIUMINT(3, "MEDIUMINT"),
    INT(4, "INT"),

    BIGINT(5, "BIGINT"),
    FLOAT(6, "FLOAT"),

    DOUBLE(7, "DOUBLE"),

    DECIMAL(8, "DECIMAL"),

    BIT(9, "BIT"),

    YEAR(10, "YEAR"),
    TIME(11, "TIME"),
    DATE(12, "DATE"),
    DATETIME(13, "DATETIME"),
    TIMESTAMP(14, "TIMESTAMP"),

    CHAR(15, "CHAR"),
    VARCHAR(16, "VARCHAR"),
    TINYTEXT(17, "TINYTEXT"),
    TEXT(18, "TEXT"),
    MEDIUMTEXT(19, "MEDIUMTEXT"),
    LONGTEXT(20, "LONGTEXT"),

    ENUM(21, "ENUM"),

    SET(22, "ENUM"),

    BINARY(23, "BINARY"),
    VARBINARY(24, "VARBINARY"),

    JSON(25, "JSON"),

    GEOMETRY(26, "GEOMETRY"),
    POINT(27, "POINT"),
    GEOMETRYCOLLECTION(28, "GEOMETRYCOLLECTION");

    @EnumValue
    private final Integer code;
    private final String type;

    DataType(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
