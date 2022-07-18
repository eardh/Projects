package com.eardh.utils;

import com.eardh.DataModelApplication;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author eardh
 * @date 2022/6/12 20:10
 */
@Component
public class Constant {

    public static String userId;

    @Value("${current.userId}")
    public void setUserId(String userId) {
        Constant.userId = userId;
    }

    public static final Map<String, String> javaType = new ConcurrentHashMap<>();

    public static final String CLASSPATH;

    static {
        File source = new ApplicationHome(DataModelApplication.class).getSource();
        CLASSPATH = source != null ? source.getParent() : "./";
    }

    static {
        javaType.put("TINYINT", "Integer");
        javaType.put("SMALLINT", "Integer");
        javaType.put("MEDIUMINT", "Integer");
        javaType.put("INT", "Integer");
        javaType.put("BIGINT", "String");
        javaType.put("FLOAT", "Float");
        javaType.put("DOUBLE", "Double");
        javaType.put("DECIMAL", "String");
        javaType.put("BIT", "String");
        javaType.put("YEAR", "Date");
        javaType.put("TIME", "Date");
        javaType.put("DATE", "Date");
        javaType.put("DATETIME", "DateTime");
        javaType.put("TIMESTAMP", "Long");
        javaType.put("CHAR", "String");
        javaType.put("VARCHAR", "String");
        javaType.put("TINYTEXT", "String");
        javaType.put("TEXT", "String");
        javaType.put("MEDIUMTEXT", "String");
        javaType.put("LONGTEXT", "String");
    }
}
